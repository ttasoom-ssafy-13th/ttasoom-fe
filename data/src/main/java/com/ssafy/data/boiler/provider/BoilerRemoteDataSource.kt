package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerCheckHistoryDto
import com.ssafy.data.boiler.model.BoilerItemDto
import com.ssafy.data.boiler.model.BoilerRecommendationDto
import com.ssafy.data.boiler.model.BoilerUsagePredictionDto
import retrofit2.Response
import javax.inject.Inject

interface BoilerRemoteDataSource {
    suspend fun getBoilerList(
        companyNames: List<String>? = null,
        certificationTypes: List<String>? = null,
        circulationTypes: List<String>? = null,
        fuelTypes: List<String>? = null
    ): Result<List<BoilerItemDto>>

    suspend fun getUsagePrediction(): Result<BoilerUsagePredictionDto>

    suspend fun getUsageRecommendations(): Result<BoilerRecommendationDto>

    suspend fun getCheckHistory(): Result<List<BoilerCheckHistoryDto>>
}
