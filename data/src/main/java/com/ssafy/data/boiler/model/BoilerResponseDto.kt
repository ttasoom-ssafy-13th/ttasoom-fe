package com.ssafy.data.boiler.model

import com.google.gson.annotations.SerializedName

data class BoilerResponseDto(
    @SerializedName("data")
    val data: List<BoilerItemDto>
)
