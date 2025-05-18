package com.ssafy.domain.boiler.model

import java.time.LocalDateTime

data class BoilerCheckHistory(
    val boilerValue: Int,           // 보일러 값
    val createdAt: LocalDateTime    // 생성 시간
) 