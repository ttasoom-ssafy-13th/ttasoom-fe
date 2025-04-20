package com.ssafy.data.userInfo.provider

import com.ssafy.data.userInfo.model.UserInfoDto
import com.ssafy.domain.userInfo.model.UserInfo

class FakeUserInfoDataSourceImpl()  : UserInfoDataSource{

    var testData =UserInfoDto("서현호",2,3)

    override suspend fun getUserInfo(): Result<UserInfoDto> {
       return Result.success(testData)
    }

    override suspend fun putUserInfo(userInfo : UserInfoDto): Result<UserInfoDto> {
        return Result.success(userInfo)
    }


}