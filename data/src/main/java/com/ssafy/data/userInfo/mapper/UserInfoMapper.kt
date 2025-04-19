package com.ssafy.data.userInfo.mapper


import com.ssafy.data.userInfo.model.UserInfoDto
import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.login.model.User
import com.ssafy.domain.userInfo.model.UserInfo

fun UserInfoDto.toDomain(): UserInfo{

    return UserInfo(
        username  = username,
        mileage= mileage,
        level = level
    )

}
