package com.ssafy.domain.auth.repository

import com.ssafy.domain.auth.model.AuthProviderType
import com.ssafy.domain.auth.model.User

interface AuthRepository {
    suspend fun login(type: AuthProviderType, token: String?): Result<User>
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun getCurrentUser(): User?
}
