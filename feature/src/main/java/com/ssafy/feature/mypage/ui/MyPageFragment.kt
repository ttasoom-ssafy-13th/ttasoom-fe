package com.ssafy.feature.mypage.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ssafy.feature.R
import com.ssafy.feature.databinding.FragmentMypageBinding
import com.ssafy.feature.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MyPageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyPageViewModel by viewModels()

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

        viewModel.loadMileageStatus().also { Log.d("TAG", "onViewCreated: load called") }
        viewModel.loadMileageHistory()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessage.collectLatest { message ->
                if (message != null) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    // 메시지를 소비한 뒤 초기화
                    viewModel.clearErrorMessage()
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mileageStatus.collectLatest { status ->
                if (status == null) {
                    binding.tvName.text = "마일리지 정보 없음"
                    binding.tvGrade.text = ""
                    binding.tvGreeting.text = "마일리지 정보가 없습니다. 자가검침을 해보세요!"
                } else {
                    binding.tvName.text = "안녕하세요 ${status.userId}님"
                    binding.tvGrade.text = status.grade
                    binding.tvGreeting.text = "다시 오신 것을 환영합니다! 자가검침을 하고 보상을 받으세요\n총 마일리지 포인트: ${status.totalMileage}"
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mileageHistory.collectLatest { history ->
                if (history.isEmpty()) {
                    binding.barChart.visibility = View.GONE
                    binding.tvStreak.text = "출석 기록이 없습니다."
                    return@collectLatest
                } else {
                    binding.barChart.visibility = View.VISIBLE
                }

                val attendanceHistory = history
                    .filter { it.type == "attendance" }
                    .sortedBy { it.createdAt }

                val formatter = DateTimeFormatter.ofPattern("MM/dd")
                val streak = viewModel.calculateAttendanceStreak(attendanceHistory)
                binding.tvStreak.text = "$streak 일 연속 출석 중"

                var cumulativeSum = 0
                val grouped = attendanceHistory.groupBy { it.createdAt.toLocalDate() }
                val sortedDates = grouped.keys.sorted()

                val entries = mutableListOf<Entry>()
                val labels = mutableListOf<String>()

                sortedDates.forEachIndexed { index, date ->
                    val daySum = grouped[date]?.sumOf { it.amount } ?: 0
                    cumulativeSum += daySum
                    entries.add(Entry(index.toFloat(), cumulativeSum.toFloat()))
                    runCatching {
                        labels.add(date.format(formatter))
                    }.onFailure {
                        Log.d("TAG", "onViewCreated: $it")
                    }
                }

                val dataSet = LineDataSet(entries, "총 마일리지 추이").apply {
                    setDrawFilled(true)
                    setDrawCircles(true)
                    circleRadius = 4f
                    lineWidth = 2f
                    mode = LineDataSet.Mode.LINEAR
                }

                val lineData = LineData(dataSet)

                binding.barChart.apply {
                    clear()
                    data = lineData
                    xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                    xAxis.granularity = 1f
                    xAxis.setDrawGridLines(false)
                    xAxis.setDrawAxisLine(true)
                    axisLeft.axisMinimum = 0f
                    axisRight.isEnabled = false
                    description.isEnabled = false
                    legend.isEnabled = false
                    notifyDataSetChanged()
                    invalidate()
                }
            }
        }


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
