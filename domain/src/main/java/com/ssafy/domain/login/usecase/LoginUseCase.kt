package com.ssafy.domain.login.usecase

import com.ssafy.domain.login.model.AuthProviderType
import com.ssafy.domain.login.model.User
import com.ssafy.domain.login.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend fun execute(type: AuthProviderType, token: String?): Result<User> {
        return repository.login(type, token)
    }
}
