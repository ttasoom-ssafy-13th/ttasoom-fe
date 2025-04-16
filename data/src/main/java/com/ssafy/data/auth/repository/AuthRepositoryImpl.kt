package com.ssafy.data.auth.repository


import com.ssafy.data.auth.mapper.toDomain
import com.ssafy.data.auth.provider.AuthProvider
import com.ssafy.domain.auth.model.AuthProviderType
import com.ssafy.domain.auth.model.User
import com.ssafy.domain.auth.repository.AuthRepository

class AuthRepositoryImpl(
    private val providers: Map<AuthProviderType, AuthProvider>
) : AuthRepository {

    override suspend fun login(type: AuthProviderType, token: String?): Result<User> {
        val provider = providers[type]
            ?: return Result.failure(Exception("Unsupported auth provider: $type"))
        return provider.login(token).map { it.toDomain() }
    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): User? {
        TODO("Not yet implemented")
    }
}
