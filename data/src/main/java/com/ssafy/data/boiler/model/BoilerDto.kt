package com.ssafy.data.boiler.model

data class BoilerDto(
    val name: String,               // 제품명
    val company: String,            // 기업명
    val certification: String,      // 인증종류
    val circulationType: String,    // 순환방식
    val fuel: String,               // 사용연료
    val certificationDate: String,  // 인증 시작일
    val imageUrl: String            // 이미지 url
)