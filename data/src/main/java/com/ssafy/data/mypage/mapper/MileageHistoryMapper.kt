package com.ssafy.data.mypage.mapper

import com.ssafy.data.mypage.model.MileageHistoryDto
import com.ssafy.domain.mypage.model.MileageHistory

fun MileageHistoryDto.toDomain(): MileageHistory {
    return MileageHistory(
        id = id,
        userId = userId,
        amount = amount,
        type = type,
        description = description,
        createdAt = createdAt
    )
}
