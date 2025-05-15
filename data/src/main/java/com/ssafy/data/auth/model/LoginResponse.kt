package com.ssafy.data.auth.model

data class LoginResponse(
    val access_token: String,
    val refresh_token: String,
    val token_type: String,
    val user: UserResponse
)