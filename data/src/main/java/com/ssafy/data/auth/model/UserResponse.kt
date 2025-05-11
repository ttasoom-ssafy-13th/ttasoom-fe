package com.ssafy.data.auth.model

data class UserResponse(
    val uid: String,
    val email: String,
    val email_verified: Boolean,
    val name: String?,
    val picture: String?,
    val provider_id: String?
)