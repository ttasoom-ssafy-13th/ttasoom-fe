package com.ssafy.data.userInfo.provider

import com.ssafy.data.userInfo.model.UserInfoDto
import com.ssafy.data.userInfo.model.UserInfoResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserInfoApiService {
    //Header에 토큰값 필수일듯

    @POST("/userInfo")
    suspend fun postUserInfo(
        @Query("mileageType") mileageType: Int
    ): UserInfoResponseDto

    @GET("/userInfo")
    suspend fun getUserInfo() :UserInfoResponseDto

}