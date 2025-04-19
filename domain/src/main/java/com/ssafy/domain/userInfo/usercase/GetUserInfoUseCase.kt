package com.ssafy.domain.userInfo.usercase

import com.ssafy.domain.userInfo.model.UserInfo
import com.ssafy.domain.userInfo.repository.UserInfoRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: UserInfoRepository
) {
    suspend operator fun invoke(): Result<UserInfo> {
        return repository.getUserInfo()
    }
}