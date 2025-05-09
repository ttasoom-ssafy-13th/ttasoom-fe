package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto

// BoilerRemoteDataSource.kt
interface BoilerRemoteDataSource {
    suspend fun getBoilerList(
        companyName: List<String>? = null,
        certificationType: List<String>? = null,
        circulationType: List<String>? = null,
        fuelType: List<String>? = null
    ): Result<List<BoilerItemDto>>
}
