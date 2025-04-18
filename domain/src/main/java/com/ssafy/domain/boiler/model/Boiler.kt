package com.ssafy.domain.boiler.model

data class Boiler(
    val companyName: String,
    val certificationType: String, // 인증 종류
    val circulationType: String, // 순환 바식
    val fuelType: String, // 사용 연료
    val productName: String, // 제품명
    val certificationStartDate: String, // 인증 시작일
    val imageUrl: String
)
