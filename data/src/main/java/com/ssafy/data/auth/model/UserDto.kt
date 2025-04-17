package com.ssafy.data.auth.model

import com.ssafy.domain.auth.model.AuthProviderType

data class UserDto(
    val uid: String?,
    val email: String?,
    val name: String?,
    val provider: AuthProviderType
)