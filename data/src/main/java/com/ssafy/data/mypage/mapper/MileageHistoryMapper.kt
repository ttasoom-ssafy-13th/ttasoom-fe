package com.ssafy.data.mypage.mapper

import android.util.Log
import com.ssafy.data.mypage.model.MileageHistoryDto
import com.ssafy.domain.mypage.model.MileageHistory
import java.time.LocalDateTime
import java.time.OffsetDateTime

fun MileageHistoryDto.toDomain(): MileageHistory {
    return MileageHistory(
        id = id,
        userId = userId,
        amount = amount,
        type = type,
        description = description,
        createdAt = createdAt.toLocalDateTime()
    )
}

fun String.toLocalDateTime(): LocalDateTime {
    return OffsetDateTime.parse(this).toLocalDateTime()
}
