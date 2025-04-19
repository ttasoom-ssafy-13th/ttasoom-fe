package com.ssafy.data.userInfo.provider

import com.ssafy.data.userInfo.model.UserInfoDto

interface UserInfoDataSource {

    suspend fun getUserInfo() : Result<UserInfoDto>
    suspend fun postUserInfo(mileageType :Int): Result<UserInfoDto>
}