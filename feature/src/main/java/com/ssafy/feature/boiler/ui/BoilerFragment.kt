package com.ssafy.feature.boiler.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ssafy.feature.R
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.ssafy.di.navigation.Navigator
import com.ssafy.feature.boiler.adapter.BoilerAdapter
import com.ssafy.feature.boiler.viewmodel.BoilerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.feature.databinding.BottomSheetFilterBinding
import com.ssafy.feature.boiler.adapter.FilterOptionAdapter

@AndroidEntryPoint
class BoilerFragment : Fragment() {

    private lateinit var companyButton: MaterialButton
    private lateinit var certButton: MaterialButton
    private lateinit var circButton: MaterialButton
    private lateinit var fuelButton: MaterialButton
    private lateinit var chipGroup: ChipGroup
    private val viewModel: BoilerViewModel by viewModels()
    private lateinit var boilerAdapter: BoilerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_boiler, container, false)

        // 버튼과 ChipGroup 연결
        companyButton = view.findViewById(R.id.btn_companyName)
        certButton = view.findViewById(R.id.btn_certificationType)
        circButton = view.findViewById(R.id.btn_circulationMethod)
        fuelButton = view.findViewById(R.id.btn_fuelType)
        chipGroup = view.findViewById(R.id.chipGroup_filters)

        // 드롭다운 설정
        setupFilterButton(companyButton, R.array.company_names, "회사명")
        setupFilterButton(certButton, R.array.certificate_types, "인증 종류")
        setupFilterButton(circButton, R.array.circulation_types, "순환 방식")
        setupFilterButton(fuelButton, R.array.fuel_types, "연료 종류")

        val searchButton = view.findViewById<Button>(R.id.btn_search)
        searchButton.setOnClickListener {
            val filters = getSelectedFilters()
            Log.d("BoilerFragment", "🔍 선택된 필터: $filters")

            viewModel.fetchFilteredBoilers(
                companyNames = filters["company_name"],
                certificationTypes = filters["certification_type"],
                circulationTypes = filters["circulation_type"],
                fuelTypes = filters["fuel_type"]
            )

        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerBoiler)
        boilerAdapter = BoilerAdapter()
        recyclerView.adapter = boilerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.boilerListResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { list ->
                    Log.d("BoilerFragment", "✅ 서버 응답 성공: ${list.size}개 아이템")
                    boilerAdapter.submitList(list)
                },
                onFailure = { e ->
                    Log.e("BoilerFragment", "❌ 서버 요청 실패: ${e.message}")
                }
            )
        }


        return view
    }

    // ✅ LiveData observe → 결과 로그 출력
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerBoiler)
        boilerAdapter = BoilerAdapter()
        recyclerView.adapter = boilerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 🔍 필터 결과 관찰
        viewModel.boilerListResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { list ->
                    Log.d("BoilerFragment", "✅ 서버 응답 성공: ${list.size}개 아이템")

                    // 기본 정렬: 오름차순
                    val sortedList = viewModel.getSortedBoilers(ascending = true)
                    boilerAdapter.submitList(sortedList)
                },
                onFailure = { e ->
                    Log.e("BoilerFragment", "❌ 서버 요청 실패: ${e.message}")
                }
            )
        }

        // 🔀 정렬 라디오 버튼
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggle_sort_group)

        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            val isAscending = checkedId == R.id.btn_sort_asc
            val sortedList = viewModel.getSortedBoilers(isAscending)
            boilerAdapter.submitList(sortedList)
        }

    }


    private fun setupFilterButton(button: MaterialButton, arrayResId: Int, title: String) {
        button.setOnClickListener {
            showFilterBottomSheet(arrayResId, title) { selected ->
                addChip(selected)
            }
        }
    }

    private fun showFilterBottomSheet(arrayResId: Int, title: String, onOptionSelected: (String) -> Unit) {
        val dialog = BottomSheetDialog(requireContext())
        val binding = BottomSheetFilterBinding.inflate(layoutInflater)
        
        binding.tvFilterTitle.text = title
        
        val options = resources.getStringArray(arrayResId).toList()
        val adapter = FilterOptionAdapter(options) { selected ->
            onOptionSelected(selected)
            dialog.dismiss()
        }
        
        binding.recyclerFilterOptions.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        
        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        
        binding.btnApply.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.setContentView(binding.root)
        dialog.show()
    }

    fun getSelectedFilters(): Map<String, List<String>> {
        val filters = mutableMapOf<String, MutableList<String>>()

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            val text = chip.text.toString()

            companyNameMap[text]?.let {
                filters.getOrPut("company_name") { mutableListOf() }.add(it)
            }
            certificationTypeMap[text]?.let {
                filters.getOrPut("certification_type") { mutableListOf() }.add(it)
            }
            circulationTypeMap[text]?.let {
                filters.getOrPut("circulation_type") { mutableListOf() }.add(it)
            }
            fuelTypeMap[text]?.let {
                filters.getOrPut("fuel_type") { mutableListOf() }.add(it)
            }
        }

        return filters
    }

    private fun addChip(text: String) {
        // 중복 방지
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if (chip.text == text) return
        }

        val chip = Chip(requireContext()).apply {
            this.text = text
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                chipGroup.removeView(this)
            }
        }
        chipGroup.addView(chip)
    }

    val companyNameMap = mapOf(
        "경동나비엔" to "(주)경동나비엔",
        "귀뚜라미" to "(주)귀뚜라미",
        "알토앤대우" to "(주)알토엔대우",
        "임코보일러" to "(주)임코보일러",
        "대성쎌틱" to "대성쎌틱에너시스(주)",
        "린나이" to "린나이코리아(주)"
    )

    val certificationTypeMap = mapOf(
        "1종" to "1종",
        "2종" to "2종"
    )

    val circulationTypeMap = mapOf(
        "개방식" to "개방식",
        "차단식" to "차단식"
    )

    val fuelTypeMap = mapOf(
        "LNG" to "LNG",
        "LPG" to "LPG",
        "등유" to "등유"
    )

}
