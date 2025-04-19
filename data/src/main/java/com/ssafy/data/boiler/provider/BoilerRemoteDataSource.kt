package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto

interface BoilerRemoteDataSource {
    suspend fun getBoilerList(
        companyName: String? = null, //이거 list로 하면 어떨까
        certificationType: String? = null,
        circulationType: String? = null,
        fuelType: String? = null
    ): Result<List<BoilerItemDto>>
}
