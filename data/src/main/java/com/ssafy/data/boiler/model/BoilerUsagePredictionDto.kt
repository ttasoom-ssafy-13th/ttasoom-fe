package com.ssafy.data.boiler.model

import com.google.gson.annotations.SerializedName

data class BoilerUsagePredictionDto(
    @SerializedName("predicted_usage")
    val predictedUsage: Double,
    @SerializedName("predicted_cost")
    val predictedCost: Int,
    @SerializedName("base_fee")
    val baseFee: Int,
    @SerializedName("unit_price")
    val unitPrice: Double,
    @SerializedName("vat_rate")
    val vatRate: Double,
    @SerializedName("vat_amount")
    val vatAmount: Int,
    @SerializedName("total_cost")
    val totalCost: Int,
    @SerializedName("last_check_date")
    val lastCheckDate: String,
    @SerializedName("days_since_last_check")
    val daysSinceLastCheck: Int,
    @SerializedName("has_sufficient_history")
    val hasSufficientHistory: Boolean,
    val message: String
) 