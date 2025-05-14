package com.ssafy.domain.mypage.usecase

import com.ssafy.domain.mypage.model.MileageHistory
import com.ssafy.domain.mypage.repository.MileageRepository

class GetMileageHistoryUseCase(
    private val repository: MileageRepository
) {
    suspend operator fun invoke(): Result<List<MileageHistory>> {
        return repository.getMileageHistory()
    }
}
