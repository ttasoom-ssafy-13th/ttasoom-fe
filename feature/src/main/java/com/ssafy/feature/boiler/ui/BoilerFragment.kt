package com.ssafy.feature.boiler.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ssafy.feature.R
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.feature.boiler.adapter.BoilerAdapter
import com.ssafy.feature.boiler.viewmodel.BoilerViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        setupDropdown(companyButton, R.array.company_names)
        setupDropdown(certButton, R.array.certificate_types)
        setupDropdown(circButton, R.array.circulation_types)
        setupDropdown(fuelButton, R.array.fuel_types)

        val searchButton = view.findViewById<Button>(R.id.btn_search)
        searchButton.setOnClickListener {
            val filters = getSelectedFilters()
            Log.d("BoilerFragment", "🔍 선택된 필터: $filters")

            viewModel.fetchFilteredBoilers(
                companyName = filters["companyName"],
                certificationType = filters["certificationType"],
                circulationType = filters["circulationType"],
                fuelType = filters["fuelType"]
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

        viewModel.boilerListResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { list ->
                    Log.d("BoilerFragment", "✅ 서버 응답 성공: ${list.size}개 아이템")
                    // 🔽 여기에 RecyclerView adapter.submitList(list) 가능
                },
                onFailure = { e ->
                    Log.e("BoilerFragment", "❌ 서버 요청 실패: ${e.message}")
                }
            )
        }
    }

    private fun setupDropdown(button: MaterialButton, arrayResId: Int) {
        button.setOnClickListener {
            val items = resources.getStringArray(arrayResId)
            AlertDialog.Builder(requireContext())
                .setTitle("선택하세요")
                .setItems(items) { _, which ->
                    val selected = items[which]
                    addChip(selected)
                }
                .show()
        }
    }

    fun getSelectedFilters(): Map<String, String?> {
        val filters = mutableMapOf<String, String?>()

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            val text = chip.text.toString()

            companyNameMap[text]?.let { filters["companyName"] = it }
            certificationTypeMap[text]?.let { filters["certificationType"] = it }
            circulationTypeMap[text]?.let { filters["circulationType"] = it }
            fuelTypeMap[text]?.let { filters["fuelType"] = it }
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
        "경동나비엔" to "KYUNG_DONG",
        "귀뚜라미" to "(주)귀뚜라미",
        "대성쎌틱" to "DAESUNG",
        "린나이" to "RINNAI"
    )

    val certificationTypeMap = mapOf(
        "일반" to "NORMAL",
        "저녹스" to "LOW_NOX",
        "콘덴싱" to "CONDENSING"
    )

    val circulationTypeMap = mapOf(
        "자연순환식" to "NATURAL",
        "강제순환식" to "FORCED"
    )

    val fuelTypeMap = mapOf(
        "도시가스" to "CITY_GAS",
        "기름" to "OIL",
        "LPG" to "LPG"
    )

}
