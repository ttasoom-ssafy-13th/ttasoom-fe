package com.ssafy.data.auth.mapper

import com.ssafy.data.auth.model.UserDto
import com.ssafy.data.auth.model.UserResponse
import com.ssafy.domain.auth.model.User


fun UserDto.toDomain(): User = User(
    uid = uid ?: "",
    email = email ?: ""
)

fun UserResponse.toDomain(): User = User(
    uid = uid,
    email = email
)