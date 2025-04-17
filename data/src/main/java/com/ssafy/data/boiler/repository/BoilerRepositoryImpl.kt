package com.ssafy.data.boiler.repository

import com.ssafy.data.boiler.mapper.toDomain
import com.ssafy.data.boiler.provider.BoilerProvider
import com.ssafy.domain.bolier.model.Boiler
import com.ssafy.domain.bolier.repository.BoilerRepository

class BoilerRepositoryImpl(
    private val provider: BoilerProvider
) : BoilerRepository {

    override suspend fun getAllBoilers(): List<Boiler> {
        return provider.getBoilerList().map { it.toDomain() }
    }

    override suspend fun getSubBoilers(
        company: String,
        certification: String,
        circulationType: String,
        fuel: String
    ): List<Boiler> {
        return provider.getBoilerList()
            .map { it.toDomain() }
            .filter {
                (company.isBlank() || it.company == company) &&
                        (certification.isBlank() || it.certification == certification) &&
                        (circulationType.isBlank() || it.circulationType == circulationType) &&
                        (fuel.isBlank() || it.fuel == fuel)
            }
    }
}
