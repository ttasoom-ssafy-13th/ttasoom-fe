package com.ssafy.domain.bolier.usecase

import com.ssafy.domain.bolier.model.Boiler
import com.ssafy.domain.bolier.repository.BoilerRepository


class GetBoilerListUseCase(private val repository: BoilerRepository) {
    suspend fun execute(): List<Boiler> {
        return repository.getAllBoilers()
    }

    suspend fun filter(
        company: String,
        certification: String,
        circulationType: String,
        fuel: String
    ): List<Boiler> {
        return repository.getSubBoilers(company, certification, circulationType, fuel)
    }
}
