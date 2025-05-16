package com.ssafy.domain.mypage.model

import java.time.LocalDateTime

data class MileageStatus(
    val grade: String,
    val lastAttendance: LocalDateTime,     // ✅ 수정: last_attendance → lastAttendance
    val totalMileage: Int,          // ✅ 수정: total_mileage → totalMileage
    val userId: String              // ✅ 수정: user_id → userId
)


