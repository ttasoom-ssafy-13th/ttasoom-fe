package com.ssafy.domain.mypage.repository

import com.ssafy.domain.mypage.model.MileageHistory
import com.ssafy.domain.mypage.model.MileageStatus

interface MileageRepository {
    suspend fun getMileageStatus(): Result<MileageStatus>
    suspend fun getMileageHistory(): Result<List<MileageHistory>>
    suspend fun checkAttendance(boilerValue: Int): Result<MileageHistory>
}