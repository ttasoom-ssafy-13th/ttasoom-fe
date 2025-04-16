package com.ssafy.data.auth.provider.auth

import com.ssafy.data.auth.model.UserDto

interface AuthProvider {
    suspend fun login(token: String?): Result<UserDto>
}
