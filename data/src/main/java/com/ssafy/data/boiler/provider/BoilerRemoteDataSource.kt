package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto

interface BoilerRemoteDataSource {
    suspend fun getBoilerList(
        companyName: String? = null,
        certificationType: String? = null,
        circulationType: String? = null,
        fuelType: String? = null
    ): Result<List<BoilerItemDto>>
}
