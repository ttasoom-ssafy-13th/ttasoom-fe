package com.ssafy.data.userInfo.provider

import com.ssafy.data.userInfo.model.UserInfoDto

class FakeUserInfoDataSourceImpl()  : UserInfoDataSource{

    var testData =UserInfoDto("서현호",2,3)

    override suspend fun getUserInfo(): Result<UserInfoDto> {
       return Result.success(testData)
    }

    override suspend fun postUserInfo(mileageType: Int): Result<UserInfoDto> {
        var testData1=UserInfoDto("최윤석",1,2)

        return Result.success(testData1)
    }


}