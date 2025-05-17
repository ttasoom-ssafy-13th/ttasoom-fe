package com.ssafy.data.boiler.model

import com.google.gson.annotations.SerializedName

data class BoilerRecommendationDto(
    @SerializedName("current_usage")
    val currentUsage: Double,
    @SerializedName("average_daily_usage")
    val averageDailyUsage: Double,
    @SerializedName("recommendations")
    val recommendations: List<String>,
    @SerializedName("estimated_savings")
    val estimatedSavings: Int,
    @SerializedName("message")
    val message: String
)