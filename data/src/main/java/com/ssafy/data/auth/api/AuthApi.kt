package com.ssafy.data.auth.api

import com.ssafy.data.auth.model.LoginRequest
import com.ssafy.data.auth.model.LoginResponse
import com.ssafy.data.auth.model.UserDto
import com.ssafy.data.auth.model.UserRegisterRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("/auth/verify")
    suspend fun verifyToken(): UserDto

    @POST("/auth/register")
    suspend fun registerUser(
        @Body request: UserRegisterRequestDto
    ): Response<Unit>
}