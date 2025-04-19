package com.ssafy.data.userInfo.provider

import com.ssafy.data.userInfo.model.UserInfoDto

class FakeUserInfoDataSourceImpl  : UserInfoDataSource{

    private val testData =UserInfoDto("서현호",2,3)

    override suspend fun getUserInfo(): Result<UserInfoDto> {
       return Result.success(testData)
    }

    override suspend fun postUserInfo(mileageType: Int): Result<UserInfoDto> {
        return Result.success(testData)
    }


}