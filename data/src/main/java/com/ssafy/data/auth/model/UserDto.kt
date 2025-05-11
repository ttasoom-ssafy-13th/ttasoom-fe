package com.ssafy.data.auth.model


data class UserDto(
    val uid: String,
    val email: String?,
    val email_verified: Boolean,
    val name: String?,
    val phone_number: String?
)