package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto

class BoilerRemoteDataSourceImpl(
    private val api: BoilerApiService
) : BoilerRemoteDataSource {

    override suspend fun getBoilerList(
        companyName: String?,
        certificationType: String?,
        circulationType: String?,
        fuelType: String?
    ): Result<List<BoilerItemDto>> {
        return try {
            val response = api.getBoilerList(
                companyName, certificationType, circulationType, fuelType
            )
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}



