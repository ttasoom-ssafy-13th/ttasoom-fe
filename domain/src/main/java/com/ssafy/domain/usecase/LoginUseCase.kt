package com.ssafy.domain.usecase

import com.ssafy.domain.model.AuthProviderType
import com.ssafy.domain.model.User
import com.ssafy.domain.repository.AuthRepository


class LoginUseCase(private val repository: AuthRepository) {
    suspend fun execute(type: AuthProviderType, token: String?): Result<User> {
        return repository.login(type, token)
    }
}
