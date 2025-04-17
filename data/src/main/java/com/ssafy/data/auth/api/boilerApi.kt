package com.ssafy.data.auth.api

import com.ssafy.data.auth.model.BoilerDto
import com.ssafy.domain.model.BoilerFilter
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query


interface boilerApi {

    @GET("boiler/")
    suspend fun getBoiler(
        @Query("company") company: List<String>?,
        @Query("authNum") authNum: Int?,
        @Query("operateMethod") operateMethod: String?,
        @Query("fuel") fuel: List<String>?,
        @Query("authStartDate") authStartDate: String?
    ): Response<MutableList<BoilerDto>>

}