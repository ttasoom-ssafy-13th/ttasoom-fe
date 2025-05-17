package com.ssafy.data.mypage.mapper

import com.ssafy.data.mypage.model.MileageStatusDto
import com.ssafy.domain.mypage.model.MileageStatus
import com.ssafy.data.mypage.mapper.toLocalDateTime

fun MileageStatusDto.toDomain(): MileageStatus {
    return MileageStatus(
        grade = this.grade,
        lastAttendance = this.lastAttendance.toLocalDateTime(),
        totalMileage = this.totalMileage,
        userId = this.userId,
        mileageToNextGrade = this.mileageToNextGrade,
        currentGradeMaxMileage = this.currentGradeMaxMileage,
        nextGrade = this.nextGrade
    )
}
