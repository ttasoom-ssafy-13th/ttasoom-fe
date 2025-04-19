package com.ssafy.data.userInfo.provider

import com.ssafy.data.boiler.model.BoilerItemDto
import com.ssafy.data.userInfo.model.UserInfoDto
import com.ssafy.domain.userInfo.model.UserInfo

class UserInfoDataSourceImpl(private val api: UserInfoApiService) :
    UserInfoDataSource {


    override suspend fun getUserInfo(): Result<UserInfoDto> {
        return try {
            val response = api.getUserInfo()
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postUserInfo(mileageType: Int): Result<UserInfoDto> {

        return try {
            val response = api.postUserInfo(mileageType)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}