package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerItemDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


// BoilerApiService.kt
interface BoilerApiService {
    @GET("/boilers")
    suspend fun getFilteredBoilers(
        @Query("company_name") companyName: List<String>?,
        @Query("certification_type") certificationType: List<String>?,
        @Query("circulation_type") circulationType: List<String>?,
        @Query("fuel_type") fuelType: List<String>?
    ): Response<List<BoilerItemDto>>
}
