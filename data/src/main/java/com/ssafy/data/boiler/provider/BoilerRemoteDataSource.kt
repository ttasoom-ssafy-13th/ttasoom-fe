package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto

interface BoilerRemoteDataSource {
    suspend fun getBoilerList(): Result<List<BoilerItemDto>>
}
