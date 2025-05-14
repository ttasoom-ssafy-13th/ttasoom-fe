package com.ssafy.data.mypage.mapper

import com.ssafy.data.mypage.model.MileageStatusDto
import com.ssafy.domain.mypage.model.MileageStatus

fun MileageStatusDto.toDomain(): MileageStatus {
    return MileageStatus(
        grade = this.grade,
        lastAttendance = this.lastAttendance,
        totalMileage = this.totalMileage,
        userId = this.userId
    )
}