package com.ssafy.data.auth.mapper

import com.ssafy.data.auth.model.UserDto
import com.ssafy.domain.model.User


fun UserDto.toDomain(): User = User(
    uid = uid ?: "",
    email = email ?: "",
    provider = provider
)
