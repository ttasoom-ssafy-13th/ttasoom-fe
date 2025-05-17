package com.ssafy.data.weather.model

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("min_temp") val minTemp: String,
    @SerializedName("max_temp") val maxTemp: String,
    @SerializedName("humidity") val humidity: String
)

data class WeatherResponseDto(
    @SerializedName("weather") val weather: WeatherDto
)
