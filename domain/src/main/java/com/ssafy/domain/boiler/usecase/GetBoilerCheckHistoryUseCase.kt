package com.ssafy.domain.boiler.usecase

import com.ssafy.domain.boiler.model.BoilerCheckHistory
import com.ssafy.domain.boiler.repository.BoilerRepository
import javax.inject.Inject

class GetBoilerCheckHistoryUseCase @Inject constructor(
    private val boilerRepository: BoilerRepository
) {
    suspend operator fun invoke(): Result<List<BoilerCheckHistory>> {
        return boilerRepository.getCheckHistory()
    }
} 