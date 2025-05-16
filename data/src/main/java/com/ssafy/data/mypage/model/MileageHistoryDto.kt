package com.ssafy.data.mypage.model

import com.google.gson.annotations.SerializedName

data class MileageHistoryDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("created_at")
    val createdAt: String
)
