package com.ssafy.feature.mypage.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.ssafy.domain.boiler.model.BoilerCheckHistory
import com.ssafy.feature.R
import com.ssafy.feature.databinding.FragmentBoilerSolutionBinding
import com.ssafy.feature.mypage.viewmodel.BoilerSolutionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class BoilerSolutionFragment : Fragment() {

    private var _binding: FragmentBoilerSolutionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BoilerSolutionViewModel by viewModels()
    private val dateFormatter = DateTimeFormatter.ofPattern("MM/dd", Locale.getDefault())
    private val chartColor = Color.rgb(0, 128, 0) // 진한 녹색

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoilerSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChart()
        observeViewModel()
    }

    private fun setupChart() {
        binding.usageChart.apply {
            description.isEnabled = false
            legend.isEnabled = true
            setTouchEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
            }

            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 0f
            }

            axisRight.isEnabled = false
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                showLoading(isLoading)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.checkHistory.collectLatest { history ->
                updateChart(history)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.predictedUsage.collectLatest { usage ->
                binding.tvPredictedUsage.text = usage
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.predictedCost.collectLatest { cost ->
                binding.tvPredictedCost.text = cost
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.evaluation.collectLatest { evaluation ->
                binding.tvEvaluation.text = evaluation
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tips.collectLatest { tips ->
                binding.tvTips.text = tips
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.savings.collectLatest { savings ->
                binding.tvSavings.text = savings
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.precautions.collectLatest { precautions ->
                binding.tvPrecautions.text = precautions
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateChart(history: List<BoilerCheckHistory>) {
        val sortedHistory = history.sortedBy { it.createdAt }
        val entries = sortedHistory.mapIndexed { index, check ->
            Entry(index.toFloat(), check.boilerValue.toFloat())
        }

        val dataSet = LineDataSet(entries, "보일러 사용량").apply {
            color = chartColor
            setCircleColor(chartColor)
            lineWidth = 2f
            circleRadius = 2f
            setDrawCircleHole(false)
            valueTextSize = 9f
        }

        binding.usageChart.apply {
            data = LineData(dataSet)
            xAxis.valueFormatter = IndexAxisValueFormatter(
                sortedHistory.map { dateFormatter.format(it.createdAt) }
            )
            invalidate()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.tvLoading.isVisible = isLoading
        binding.contentLayout.isVisible = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}