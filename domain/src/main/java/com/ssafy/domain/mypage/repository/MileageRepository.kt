package com.ssafy.domain.mypage.repository

import com.ssafy.domain.mypage.model.MileageStatus

interface MileageRepository {
    suspend fun getMileageStatus(): Result<MileageStatus>
}