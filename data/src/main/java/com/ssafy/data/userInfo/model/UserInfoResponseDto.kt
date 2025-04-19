package com.ssafy.data.userInfo.model

import com.google.gson.annotations.SerializedName

data class UserInfoResponseDto(
    @SerializedName("data")
    val data :UserInfoDto
)
