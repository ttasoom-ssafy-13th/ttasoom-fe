package com.ssafy.domain.userInfo.usercase

import com.ssafy.domain.userInfo.model.UserInfo
import com.ssafy.domain.userInfo.repository.UserInfoRepository
import javax.inject.Inject

class PutUserInfoUseCase @Inject constructor(
    private val repository: UserInfoRepository
) {
    suspend operator fun invoke(userInfo: UserInfo): Result<UserInfo> {
        return repository.putUserInfo(userInfo)
    }
}