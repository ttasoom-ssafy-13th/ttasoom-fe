package com.ssafy.domain.userInfo.repository

import com.ssafy.domain.userInfo.model.UserInfo

interface UserInfoRepository {
    suspend fun getUserInfo() : Result<UserInfo>
    suspend fun postUserInfo(mileageType :Int): Result<UserInfo>
}