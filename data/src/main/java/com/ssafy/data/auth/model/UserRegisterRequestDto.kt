package com.ssafy.data.auth.model

import com.google.gson.annotations.SerializedName

data class UserRegisterRequestDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone_number") val phoneNumber: String
)
