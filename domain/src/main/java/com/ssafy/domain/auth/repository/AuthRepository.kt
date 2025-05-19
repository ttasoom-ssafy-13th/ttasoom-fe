package com.ssafy.domain.auth.repository

import com.ssafy.domain.auth.model.User
import com.ssafy.domain.auth.model.UserRegisterInfo

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(user: UserRegisterInfo): Result<Unit>
    suspend fun verifyToken(): Result<User>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): User?
}
