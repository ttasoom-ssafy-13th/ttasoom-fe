package com.ssafy.domain.auth.usecase

import com.ssafy.domain.auth.model.AuthProviderType
import com.ssafy.domain.auth.model.User
import com.ssafy.domain.auth.repository.AuthRepository


class LoginUseCase(private val repository: AuthRepository) {
    suspend fun execute(type: AuthProviderType, token: String?): Result<User> {
        return repository.login(type, token)
    }
}
