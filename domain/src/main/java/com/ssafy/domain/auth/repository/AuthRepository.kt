package com.ssafy.domain.auth.repository

import com.ssafy.domain.auth.model.User
import com.ssafy.domain.auth.model.UserRegisterInfo

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun verifyToken(): Result<User>
    suspend fun getCurrentUser(): User?
    suspend fun register(user: UserRegisterInfo): Result<Unit>
}
