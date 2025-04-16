package com.ssafy.data.boiler.provider

import com.ssafy.data.boiler.model.BoilerResponseDto
import retrofit2.http.GET

interface BoilerApiService {
    @GET("/boilers") // 실제 서버 경로에 맞춰서 수정
    suspend fun getBoilerList(): BoilerResponseDto
}
