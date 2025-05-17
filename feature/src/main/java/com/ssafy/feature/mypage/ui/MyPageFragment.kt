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
            viewModel.mileageStatus.collectLatest { status ->
                status?.let {
                    binding.tvName.text = "안녕하세요\n${it.userId}님"
                    binding.tvGrade.text = "${it.grade} (다음 등급: ${it.nextGrade})"
                    binding.tvGreeting.text = "다시 오신 것을 환영합니다!\n자가검침을 하고 보상을 받으세요"
                    binding.tvTotalMileage.text = "총 마일리지 포인트: ${it.totalMileage}"
                    // 다음 등급까지 남은 마일리지 표시
                    binding.tvNextGrade.text = "다음 등급까지 ${it.mileageToNextGrade}마일리지 남았습니다."
                    
                    // 프로그레스바 업데이트
                    val progress = ((it.totalMileage.toFloat() / it.currentGradeMaxMileage) * 100).toInt()
                    binding.progressBar.progress = progress
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mileageHistory.collectLatest { history ->
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
                    }.onSuccess {
//                        Log.d("TAG", "onViewCreated: $it $labels")
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
                    clear() // ✅ 기존 데이터 제거
                    data = lineData
                    xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                    xAxis.granularity = 1f
                    xAxis.setDrawGridLines(false)
                    xAxis.setDrawAxisLine(true)
                    axisLeft.axisMinimum = 0f
                    axisRight.isEnabled = false
                    description.isEnabled = false
                    legend.isEnabled = false
                    notifyDataSetChanged() // ✅ 데이터 갱신 알림
                    invalidate() // ✅ 차트 다시 그리기
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
