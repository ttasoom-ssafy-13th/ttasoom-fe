package com.ssafy.domain.boiler.model

import java.time.LocalDateTime

data class BoilerRecommendation(
    val predictedUsage: String,      // 예상 사용량
    val predictedCost: String,       // 예상 비용
    val evaluation: String,          // 평가
    val tips: String,                // 절감 팁
    val savings: String,             // 예상 절약량
    val precautions: String          // 주의사항
) 