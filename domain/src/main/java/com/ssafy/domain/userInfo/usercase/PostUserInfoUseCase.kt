package com.ssafy.domain.userInfo.usercase

import com.ssafy.domain.userInfo.model.UserInfo
import com.ssafy.domain.userInfo.repository.UserInfoRepository
import javax.inject.Inject

class PostUserInfoUseCase @Inject constructor(
    private val repository: UserInfoRepository
) {
    suspend operator fun invoke(mileageType: Int): Result<UserInfo> {
        return repository.postUserInfo(mileageType)
    }
}