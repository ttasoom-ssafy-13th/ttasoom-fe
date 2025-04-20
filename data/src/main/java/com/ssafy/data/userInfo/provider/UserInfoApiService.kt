package com.ssafy.data.userInfo.provider

import com.ssafy.data.userInfo.model.UserInfoDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserInfoApiService {
    //Header에 토큰값 필수일듯

    @POST("/userInfo")
    suspend fun putUserInfo(
       @Body request : UserInfoDto
    ): UserInfoDto

    @PUT("/userInfo")
    suspend fun getUserInfo() :UserInfoDto

}