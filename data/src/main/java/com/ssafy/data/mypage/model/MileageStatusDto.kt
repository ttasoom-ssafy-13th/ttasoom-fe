package com.ssafy.data.mypage.model

import com.google.gson.annotations.SerializedName

data class MileageStatusDto(
    @SerializedName("grade")
    val grade: String,

    @SerializedName("last_attendance")
    val lastAttendance: String,

    @SerializedName("total_mileage")
    val totalMileage: Int,

    @SerializedName("user_id")
    val userId: String
)