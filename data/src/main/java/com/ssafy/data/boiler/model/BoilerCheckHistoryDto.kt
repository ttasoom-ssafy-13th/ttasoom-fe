package com.ssafy.data.boiler.model

import com.google.gson.annotations.SerializedName

data class BoilerCheckHistoryDto(
    @SerializedName("boiler_value")
    val boilerValue: Int,
    @SerializedName("created_at")
    val createdAt: String
) 