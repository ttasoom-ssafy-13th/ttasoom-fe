package com.ssafy.feature.boiler.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.feature.databinding.FragmentBoilerSolutionBinding
import com.ssafy.feature.mypage.viewmodel.BoilerSolutionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BoilerSolutionFragment : Fragment() {
    private var _binding: FragmentBoilerSolutionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BoilerSolutionViewModel by viewModels()

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
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.predictedUsage.collectLatest { usage ->
                binding.tvPredictedUsage.text = usage.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.predictedCost.collectLatest { cost ->
                binding.tvPredictedCost.text = cost.toString()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 