package com.ssafy.domain.boiler.usecase

import com.ssafy.domain.boiler.model.BoilerUsagePrediction
import com.ssafy.domain.boiler.repository.BoilerRepository
import javax.inject.Inject

class GetBoilerUsagePredictionUseCase @Inject constructor(
    private val boilerRepository: BoilerRepository
) {
    suspend operator fun invoke(): Result<BoilerUsagePrediction> {
        return boilerRepository.getUsagePrediction()
    }
} 