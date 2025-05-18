package com.ssafy.domain.auth.usecase

import com.ssafy.domain.auth.model.UserRegisterInfo
import com.ssafy.domain.auth.repository.AuthRepository

class RegisterUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(user: UserRegisterInfo): Result<Unit> {
        return authRepository.register(user)
    }
}
