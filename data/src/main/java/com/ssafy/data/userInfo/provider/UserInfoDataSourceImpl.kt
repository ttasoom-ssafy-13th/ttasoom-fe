package com.ssafy.data.userInfo.provider

import com.ssafy.data.boiler.model.BoilerItemDto
import com.ssafy.data.userInfo.model.UserInfoDto
import com.ssafy.domain.userInfo.model.UserInfo

class UserInfoDataSourceImpl(private val api: UserInfoApiService) :
    UserInfoDataSource {


    override suspend fun getUserInfo(): Result<UserInfoDto> {
        return try {
            val response = api.getUserInfo()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun putUserInfo(userInfo: UserInfoDto): Result<UserInfoDto> {

        return try {
            val response = api.putUserInfo(userInfo)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}