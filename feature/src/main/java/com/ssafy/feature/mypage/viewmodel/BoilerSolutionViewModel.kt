package com.ssafy.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.boiler.model.BoilerCheckHistory
import com.ssafy.domain.boiler.model.BoilerRecommendation
import com.ssafy.domain.boiler.repository.BoilerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoilerSolutionViewModel @Inject constructor(
    private val boilerRepository: BoilerRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _checkHistory = MutableStateFlow<List<BoilerCheckHistory>>(emptyList())
    val checkHistory: StateFlow<List<BoilerCheckHistory>> = _checkHistory.asStateFlow()

    private val _predictedUsage = MutableStateFlow("")
    val predictedUsage: StateFlow<String> = _predictedUsage.asStateFlow()

    private val _predictedCost = MutableStateFlow("")
    val predictedCost: StateFlow<String> = _predictedCost.asStateFlow()

    private val _evaluation = MutableStateFlow("")
    val evaluation: StateFlow<String> = _evaluation.asStateFlow()

    private val _tips = MutableStateFlow("")
    val tips: StateFlow<String> = _tips.asStateFlow()

    private val _savings = MutableStateFlow("")
    val savings: StateFlow<String> = _savings.asStateFlow()

    private val _precautions = MutableStateFlow("")
    val precautions: StateFlow<String> = _precautions.asStateFlow()

    init {
        loadBoilerRecommendations()
        loadCheckHistory()
    }

    private fun loadBoilerRecommendations() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                boilerRepository.getUsageRecommendations()
                    .onSuccess { recommendation ->
                        updateRecommendation(recommendation)
                    }
                    .onFailure { error ->
                        // TODO: 에러 처리
                    }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadCheckHistory() {
        viewModelScope.launch {
            boilerRepository.getCheckHistory()
                .onSuccess { history ->
                    _checkHistory.value = history
                }
                .onFailure { error ->
                    // TODO: 에러 처리
                }
        }
    }

    private fun updateRecommendation(recommendation: BoilerRecommendation) {
        _predictedUsage.value = recommendation.predictedUsage
        _predictedCost.value = recommendation.predictedCost
        _evaluation.value = recommendation.evaluation
        _tips.value = recommendation.tips
        _savings.value = recommendation.savings
        _precautions.value = recommendation.precautions
    }
}