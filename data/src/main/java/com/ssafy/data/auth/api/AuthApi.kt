package com.ssafy.data.auth.api

import com.ssafy.data.auth.model.LoginRequest
import com.ssafy.data.auth.model.LoginResponse
import com.ssafy.data.auth.model.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("/auth/verify")
    suspend fun verifyToken(): UserDto
}