package com.ssafy.domain.mypage.usecase

import com.ssafy.domain.mypage.model.MileageHistory
import com.ssafy.domain.mypage.repository.MileageRepository

class CheckAttendanceUseCase(
    private val repository: MileageRepository
) {
    suspend operator fun invoke(boilerValue: Int): Result<MileageHistory> {
        return repository.checkAttendance(boilerValue)
    }
}