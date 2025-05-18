package com.ssafy.data.weather.provider

import com.ssafy.data.weather.model.AdviceResponseDto
import com.ssafy.data.weather.model.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WeatherApi {
    @GET("/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherResponseDto

    @POST("/weather/advice")
    suspend fun getAdvice(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): AdviceResponseDto
}
