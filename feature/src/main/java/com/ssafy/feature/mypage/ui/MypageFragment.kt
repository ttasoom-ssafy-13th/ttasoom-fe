package com.ssafy.feature.mypage.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ssafy.feature.R
import com.ssafy.feature.databinding.FragmentMypageBinding
import com.ssafy.feature.mypage.viewmodel.MypageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MypageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadMileageStatus()
        viewModel.loadMileageHistory()

        // mileageStatus 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mileageStatus.collectLatest { status ->
                status?.let {
                    binding.tvName.text = "안녕하세요 ${it.userId}님"
                    binding.tvGrade.text = it.grade
                    binding.tvGreeting.text = "다시 오신 것을 환영합니다! 자가검침을 하고 보상을 받으세요\n총 마일리지 포인트: ${it.totalMileage}"
                }
            }
        }

        // mileageHistory 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mileageHistory.collectLatest { history ->
                val attendanceDates = history
                    .filter { it.type == "attendance" }
                    .map { LocalDateTime.parse(it.createdAt).toLocalDate() }
                    .toSet()

                val streakCount = attendanceDates.size
                binding.tvStreak.text = "$streakCount 일"

                val formatter = DateTimeFormatter.ofPattern("MM/dd")

                val grouped = history
                    .filter { it.type == "attendance" }
                    .groupBy {
                        LocalDateTime.parse(it.createdAt).toLocalDate()
                    }
                    .toSortedMap()

                val entries = grouped.entries.mapIndexed { index, entry ->
                    BarEntry(index.toFloat(), entry.value.sumOf { it.amount }.toFloat())
                }

                val labels = grouped.keys.map { it.format(formatter) }

                val dataSet = BarDataSet(entries, "일별 적립 마일리지")
                val barData = BarData(dataSet)

                binding.barChart.apply {
                    data = barData
                    xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                    xAxis.granularity = 1f
                    xAxis.setDrawGridLines(false)
                    xAxis.setDrawAxisLine(true)
                    axisLeft.axisMinimum = 0f
                    axisRight.isEnabled = false
                    description.isEnabled = false
                    legend.isEnabled = false
                    invalidate()
                }
            }
        }

        // 자가 검침 입력 다이얼로그
        binding.btnAttendance.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_attendance, null)
            val editText = dialogView.findViewById<EditText>(R.id.etBoilerValue)

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("보일러 자가 검침")
                .setView(dialogView)
                .setPositiveButton("제출") { _, _ ->
                    val value = editText.text.toString().toIntOrNull()
                    if (value != null) {
                        viewModel.checkAttendance(value)
                    } else {
                        Toast.makeText(requireContext(), "숫자를 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("취소", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}