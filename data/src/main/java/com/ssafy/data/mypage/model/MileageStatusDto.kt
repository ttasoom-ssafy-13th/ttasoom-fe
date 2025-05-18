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
    val userId: String,

    @SerializedName("mileage_to_next_grade")
    val mileageToNextGrade: Int,

    @SerializedName("current_grade_max_mileage")
    val currentGradeMaxMileage: Int,

    @SerializedName("next_grade")
    val nextGrade: String
)