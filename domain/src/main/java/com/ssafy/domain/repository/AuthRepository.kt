package com.ssafy.domain.repository

import com.ssafy.domain.model.AuthProviderType
import com.ssafy.domain.model.User

interface AuthRepository {
    suspend fun login(type: AuthProviderType, token: String?): Result<User>
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun getCurrentUser(): User?
}
