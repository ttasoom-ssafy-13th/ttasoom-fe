package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto
import com.ssafy.data.boiler.provider.BoilerRemoteDataSource

class BoilerRemoteDataSourceImpl(
    private val api: BoilerApiService
) : BoilerRemoteDataSource {
    override suspend fun getBoilerList(): Result<List<BoilerItemDto>> {
        return try {
            val response = api.getBoilerList()
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
