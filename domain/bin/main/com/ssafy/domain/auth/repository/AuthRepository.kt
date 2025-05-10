package com.ssafy.domain.auth.repository

import com.ssafy.domain.auth.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun verifyToken(): Result<User>
    suspend fun getCurrentUser(): User?
}
