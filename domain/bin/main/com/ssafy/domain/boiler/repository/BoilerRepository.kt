package com.ssafy.domain.boiler.repository

import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.boiler.model.BoilerCheckHistory
import com.ssafy.domain.boiler.model.BoilerRecommendation
import com.ssafy.domain.boiler.model.BoilerUsagePrediction

interface BoilerRepository {
    suspend fun getBoilerList(): Result<List<Boiler>>

    suspend fun getFilterBoilerList(
        companyNames: List<String>? = null,
        certificationTypes: List<String>? = null,
        circulationTypes: List<String>? = null,
        fuelTypes: List<String>? = null
    ): Result<List<Boiler>>

    suspend fun getUsagePrediction(): Result<BoilerUsagePrediction>

    suspend fun getUsageRecommendations(): Result<BoilerRecommendation>

    suspend fun getCheckHistory(): Result<List<BoilerCheckHistory>>
}
