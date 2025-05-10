package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto

// BoilerRemoteDataSourceImpl.kt
class BoilerRemoteDataSourceImpl(
    private val api: BoilerApiService
) : BoilerRemoteDataSource {

    override suspend fun getBoilerList(
        companyName: List<String>?,
        certificationType: List<String>?,
        circulationType: List<String>?,
        fuelType: List<String>?
    ): Result<List<BoilerItemDto>> {
        return try {
            val response = api.getFilteredBoilers(companyName, certificationType, circulationType, fuelType)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("응답 실패: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}




