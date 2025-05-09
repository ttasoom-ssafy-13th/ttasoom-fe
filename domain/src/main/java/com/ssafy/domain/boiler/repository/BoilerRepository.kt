package com.ssafy.domain.boiler.repository

import com.ssafy.domain.boiler.model.Boiler

interface BoilerRepository {
    suspend fun getBoilerList(): Result<List<Boiler>>

    suspend fun getFilterBoilerList(
        companyNames: List<String>? = null,
        certificationTypes: List<String>? = null,
        circulationTypes: List<String>? = null,
        fuelTypes: List<String>? = null
    ) : Result<List<Boiler>>
}
