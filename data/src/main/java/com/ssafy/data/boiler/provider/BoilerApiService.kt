package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BoilerApiService {
    @GET("/boilers")
    suspend fun getBoilerList(
        @Query("companyName") companyName: String? = null,
        @Query("certificationType") certificationType: String? = null,
        @Query("circulationType") circulationType: String? = null,
        @Query("fuelType") fuelType: String? = null
    ): BoilerResponseDto
}
