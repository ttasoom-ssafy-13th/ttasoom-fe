package com.ssafy.data.auth.repository


import com.google.firebase.auth.FirebaseAuth
import com.ssafy.data.auth.mapper.toDomain
import com.ssafy.data.auth.model.LoginRequest
import com.ssafy.data.auth.api.AuthApi
import com.ssafy.data.auth.provider.AuthLocalDataSource
import com.ssafy.data.auth.provider.AuthRemoteDataSource
import com.ssafy.domain.auth.model.User
import com.ssafy.domain.auth.model.UserRegisterInfo
import com.ssafy.domain.auth.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authApi: AuthApi,
    private val authLocalDataSource: AuthLocalDataSource,
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            // 1️⃣ Firebase 이메일/비밀번호 로그인
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: return Result.failure(Exception("Login failed"))

            // 2️⃣ Firebase ID Token 가져오기
            val idToken = user.getIdToken(true).await().token
                ?: return Result.failure(Exception("Failed to get ID token"))

            // 3️⃣ 백엔드 API 호출
            val response = authApi.login(LoginRequest(idToken))

            // 4️⃣ 로컬 저장소에 access, refresh token 저장
            authLocalDataSource.saveTokens(response.access_token, response.refresh_token)

            // 5️⃣ 응답받은 user를 domain User로 변환
            val domainUser = response.user.toDomain()

            Result.success(domainUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun register(user: UserRegisterInfo): Result<Unit> {
        return remoteDataSource.registerUser(user)
    }

    override suspend fun getCurrentUser(): User? {
        TODO("Not yet implemented")
    }

    override suspend fun verifyToken(): Result<User> {
        return try {
            // 1️⃣ accessToken을 검증
            val userDto = authApi.verifyToken()
            val user = userDto.toDomain()
            // 3️⃣ 성공 결과 리턴
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
