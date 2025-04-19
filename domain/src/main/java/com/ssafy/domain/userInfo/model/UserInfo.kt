package com.ssafy.domain.userInfo.model

data class UserInfo(
    val username : String,
    val mileage : Int,
    val level : Int,
)

enum class mileageType {
    ATTEND, REGISTER, CHAT
}
