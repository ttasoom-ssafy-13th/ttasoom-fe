package com.ssafy.domain

data class MileageHistory(
    val amount: Int,
    val created_at: String,
    val description: String,
    val id: String,
    val type: String,
    val user_id: String
)