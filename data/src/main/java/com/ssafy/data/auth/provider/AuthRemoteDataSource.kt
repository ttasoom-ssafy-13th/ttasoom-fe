package com.ssafy.data.auth.provider

import com.ssafy.data.auth.api.AuthApi
import com.ssafy.data.auth.mapper.toDto
import com.ssafy.domain.auth.model.UserRegisterInfo
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authApi: AuthApi
) {
    suspend fun registerUser(user: UserRegisterInfo): Result<Unit> {
        return try {
            val response = authApi.registerUser(user.toDto())

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("회원가입 실패: ${response.code()} ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
