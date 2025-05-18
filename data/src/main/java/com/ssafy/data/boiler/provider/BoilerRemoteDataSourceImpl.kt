package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerCheckHistoryDto
import com.ssafy.data.boiler.model.BoilerItemDto
import com.ssafy.data.boiler.model.BoilerRecommendationDto
import com.ssafy.data.boiler.model.BoilerUsagePredictionDto
import javax.inject.Inject

// BoilerRemoteDataSourceImpl.kt
class BoilerRemoteDataSourceImpl @Inject constructor(
    private val boilerApiService: BoilerApiService
) : BoilerRemoteDataSource {

    override suspend fun getBoilerList(
        companyNames: List<String>?,
        certificationTypes: List<String>?,
        circulationTypes: List<String>?,
        fuelTypes: List<String>?
    ): Result<List<BoilerItemDto>> = runCatching {
        boilerApiService.getFilteredBoilers(
            companyName = companyNames,
            certificationType = certificationTypes,
            circulationType = circulationTypes,
            fuelType = fuelTypes
        ).body() ?: throw Exception("응답이 null입니다.")
    }


    override suspend fun getUsagePrediction(): Result<BoilerUsagePredictionDto> = runCatching {
        boilerApiService.getUsagePrediction().body() ?: throw Exception("응답이 null입니다.")
    }

    override suspend fun getUsageRecommendations(): Result<BoilerRecommendationDto> = runCatching {
        boilerApiService.getUsageRecommendations().body() ?: throw Exception("응답이 null입니다.")
    }

    override suspend fun getCheckHistory(): Result<List<BoilerCheckHistoryDto>> = runCatching {
        boilerApiService.getCheckHistory().body() ?: throw Exception("응답이 null입니다.")
    }
}




