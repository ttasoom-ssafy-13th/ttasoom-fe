package com.ssafy.data.auth.provider

import com.ssafy.data.auth.model.UserDto
import com.ssafy.domain.model.AuthProviderType
import javax.inject.Inject

class EmailAuthProvider  @Inject constructor(): AuthProvider {

    override suspend fun login(token: String?): Result<UserDto> {
        return Result.success(UserDto("auth1-uid", "auth1@email.com", "Auth One", AuthProviderType.EMAIL))
    }
}
