package com.ssafy.domain.auth.model

data class UserRegisterInfo(
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: String
)
