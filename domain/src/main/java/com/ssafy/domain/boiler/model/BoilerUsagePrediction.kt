package com.ssafy.domain.boiler.model

import java.time.LocalDateTime

data class BoilerUsagePrediction(
    val predictedUsage: Double,      // 예상 사용량
    val predictedCost: Int,          // 예상 비용
    val baseFee: Int,                // 기본 요금
    val unitPrice: Double,           // 단위 가격
    val vatRate: Double,             // 부가세율
    val vatAmount: Int,              // 부가세액
    val totalCost: Int,              // 총 비용
    val lastCheckDate: LocalDateTime,// 마지막 점검일
    val daysSinceLastCheck: Int,     // 마지막 점검일로부터 경과일
    val hasSufficientHistory: Boolean,// 충분한 이력이 있는지 여부
    val message: String              // 메시지
) 