package com.ssafy.domain.mypage.model

data class MileageHistory(
    val id: String,
    val userId: String,
    val amount: Int,
    val type: String,
    val description: String,
    val createdAt: String // ISO 날짜 문자열 (ex: 2025-05-10T14:00:36)
)