package com.ssafy.data.mypage.provider

import com.ssafy.data.mypage.model.MileageStatusDto
import retrofit2.Response
import retrofit2.http.GET

interface MileageApiService {
    @GET("/mileage/status")
    suspend fun getMileageStatus(): Response<MileageStatusDto>
}