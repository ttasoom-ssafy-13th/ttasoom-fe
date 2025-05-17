package com.ssafy.domain.boiler.usecase

import com.ssafy.domain.boiler.model.BoilerRecommendation
import com.ssafy.domain.boiler.model.BoilerUsagePrediction
import com.ssafy.domain.boiler.repository.BoilerRepository
import javax.inject.Inject

class GetBoilerUsageRecommendationsUseCase @Inject constructor(
    private val boilerRepository: BoilerRepository
) {
    suspend operator fun invoke(): Result<BoilerRecommendation> {
        return boilerRepository.getUsageRecommendations()
    }
} 