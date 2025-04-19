package com.ssafy.data.userInfo.repository

import com.ssafy.data.userInfo.mapper.toDomain
import com.ssafy.data.userInfo.provider.UserInfoDataSource
import com.ssafy.domain.userInfo.model.UserInfo
import com.ssafy.domain.userInfo.repository.UserInfoRepository

class UserInfoRepositoryImpl(
    private val remote : UserInfoDataSource
) : UserInfoRepository{
    override suspend fun getUserInfo(): Result<UserInfo> {
        return remote.getUserInfo().map {
            it.toDomain()
        }
    }

    override suspend fun postUserInfo(mileageType: Int): Result<UserInfo> {
        return remote.postUserInfo(mileageType).map{
            it.toDomain()
        }
    }
}