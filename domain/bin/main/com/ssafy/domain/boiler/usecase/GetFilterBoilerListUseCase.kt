package com.ssafy.domain.boiler.usecase

import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.boiler.repository.BoilerRepository
import javax.inject.Inject

class GetFilterBoilerListUseCase @Inject constructor(
    private val repository: BoilerRepository
) {
    suspend operator fun invoke(
        companyNames: List<String>? = null,
        certificationTypes: List<String>? = null,
        circulationTypes: List<String>? = null,
        fuelTypes: List<String>? = null
    ): Result<List<Boiler>> {
        return repository.getFilterBoilerList(
            companyNames, certificationTypes, circulationTypes, fuelTypes
        )
    }
}

