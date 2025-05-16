package com.ssafy.domain.mypage.usecase

import com.ssafy.domain.mypage.model.MileageStatus
import com.ssafy.domain.mypage.repository.MileageRepository
import javax.inject.Inject


class GetMileageStatusUseCase @Inject constructor(
    private val repository: MileageRepository
) {
    suspend operator fun invoke(): Result<MileageStatus> {
        return repository.getMileageStatus()
    }
}