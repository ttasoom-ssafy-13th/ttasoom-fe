package com.ssafy.domain.boiler.usecase

import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.boiler.repository.BoilerRepository
import javax.inject.Inject

class GetFilterBoilerListUseCase @Inject constructor(
    private val repository: BoilerRepository
) {
    suspend operator fun invoke(
        companyName: String?,
        certificationType: String?,
        circulationType: String?,
        fuelType: String?
    ): Result<List<Boiler>> {
        return repository.getFilterBoilerList(companyName, certificationType, circulationType, fuelType)
    }
}
