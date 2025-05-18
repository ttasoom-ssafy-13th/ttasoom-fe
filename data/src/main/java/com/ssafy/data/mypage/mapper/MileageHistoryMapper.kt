package com.ssafy.data.mypage.mapper

import android.util.Log
import com.ssafy.data.mypage.model.MileageHistoryDto
import com.ssafy.domain.mypage.model.MileageHistory
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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
    return try {
        OffsetDateTime.parse(this).toLocalDateTime()
    } catch (e: DateTimeParseException) {
        try {
            LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (ex: Exception) {
            throw IllegalArgumentException("날짜 파싱 실패: $this", ex)
        }
    }
}
