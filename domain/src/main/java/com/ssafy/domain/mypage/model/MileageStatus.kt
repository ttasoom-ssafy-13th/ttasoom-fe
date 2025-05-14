package com.ssafy.domain.mypage.model

data class MileageStatus(
    val grade: String,
    val lastAttendance: String,     // ✅ 수정: last_attendance → lastAttendance
    val totalMileage: Int,          // ✅ 수정: total_mileage → totalMileage
    val userId: String              // ✅ 수정: user_id → userId
)


