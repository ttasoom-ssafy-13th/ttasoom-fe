package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerCheckHistoryDto
import com.ssafy.data.boiler.model.BoilerItemDto
import com.ssafy.data.boiler.model.BoilerRecommendationDto
import com.ssafy.data.boiler.model.BoilerUsagePredictionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


// BoilerApiService.kt
interface BoilerApiService {
    @GET("/boilers")
    suspend fun getFilteredBoilers(
        @Query("company_name") companyName: List<String>?,
        @Query("certification_type") certificationType: List<String>?,
        @Query("circulation_type") circulationType: List<String>?,
        @Query("fuel_type") fuelType: List<String>?
    ): Response<List<BoilerItemDto>>

    @GET("/boiler/usage-prediction")
    suspend fun getUsagePrediction(): Response<BoilerUsagePredictionDto>

    @GET("/boiler/usage-recommendations")
    suspend fun getUsageRecommendations(): Response<BoilerRecommendationDto>

    @GET("/boiler/check-history")
    suspend fun getCheckHistory(): Response<List<BoilerCheckHistoryDto>>
}
