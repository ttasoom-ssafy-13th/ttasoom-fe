package com.ssafy.domain.auth.usecase

import com.ssafy.domain.auth.model.User
import com.ssafy.domain.auth.repository.AuthRepository


class LoginUseCase(private val repository: AuthRepository) {
    suspend fun execute(email: String, password: String): Result<User> {
        return repository.login(email, password)
    }

    suspend fun verifyToken(): Result<User> {
        return repository.verifyToken()
    }
}
