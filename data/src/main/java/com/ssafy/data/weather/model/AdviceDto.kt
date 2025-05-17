package com.ssafy.data.weather.model

import com.google.gson.annotations.SerializedName

data class AdviceResponseDto(
    @SerializedName("advice") val advice: String
)
