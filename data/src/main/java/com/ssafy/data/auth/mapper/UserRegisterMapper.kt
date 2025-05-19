package com.ssafy.data.auth.mapper

import com.ssafy.data.auth.model.UserRegisterRequestDto
import com.ssafy.domain.auth.model.UserRegisterInfo

fun UserRegisterInfo.toDto(): UserRegisterRequestDto {
    return UserRegisterRequestDto(
        email = email,
        password = password,
        name = name,
        phoneNumber = phoneNumber
    )
}
