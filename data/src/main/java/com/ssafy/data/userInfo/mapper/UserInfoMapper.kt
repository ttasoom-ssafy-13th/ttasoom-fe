package com.ssafy.data.userInfo.mapper


import com.ssafy.data.userInfo.model.UserInfoDto
import com.ssafy.domain.userInfo.model.UserInfo

fun UserInfoDto.toDomain(): UserInfo{

    return UserInfo(
        username  = username,
        mileage= mileage,
        level = level
    )

}

fun UserInfo.toDto() : UserInfoDto{
    return UserInfoDto(
        username=this.username,
        mileage = this.mileage,
        level=this.level
    )
}
