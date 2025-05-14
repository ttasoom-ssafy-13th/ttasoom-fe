package com.ssafy.data.mypage.provider

import com.ssafy.data.mypage.model.MileageHistoryDto
import com.ssafy.data.mypage.model.MileageStatusDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MileageApiService {
    @GET("/mileage/status")
    suspend fun getMileageStatus(): Response<MileageStatusDto>

    @GET("/mileage/history")
    suspend fun getMileageHistory(): Response<List<MileageHistoryDto>>

    @POST("/mileage/attendance")
    suspend fun checkAttendance(
        @Query("boiler_value") boilerValue: Int
    ): Response<MileageHistoryDto>

}