package com.ssafy.data.userInfo.model

import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    @SerializedName("username")
    val username : String,
    @SerializedName("mileage")
    val mileage : Int,
    @SerializedName("level")
    val level : Int,
)
