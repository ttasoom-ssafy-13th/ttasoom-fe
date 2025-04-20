package com.ssafy.data.userInfo.provider

import com.ssafy.data.userInfo.model.UserInfoDto

interface UserInfoDataSource {

    suspend fun getUserInfo() : Result<UserInfoDto>
    suspend fun putUserInfo(userInfo : UserInfoDto): Result<UserInfoDto>
}