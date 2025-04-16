package com.ssafy.data.auth.provider

import com.ssafy.data.auth.model.UserDto
import com.ssafy.domain.auth.model.AuthProviderType
import javax.inject.Inject


class GoogleAuthProvider @Inject constructor() : AuthProvider {
    override suspend fun login(token: String?): Result<UserDto> {
        return Result.success(
            UserDto(
                "auth1-uid",
                "auth1@gmail.com",
                "Auth Two",
                AuthProviderType.GOOGLE
            )
        )
    }
}
