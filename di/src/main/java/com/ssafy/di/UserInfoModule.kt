package com.ssafy.di

import com.ssafy.data.userInfo.model.UserInfoDto
import com.ssafy.data.userInfo.provider.FakeUserInfoDataSourceImpl
import com.ssafy.data.userInfo.provider.UserInfoDataSource
import com.ssafy.data.userInfo.repository.UserInfoRepositoryImpl
import com.ssafy.domain.userInfo.model.UserInfo
import com.ssafy.domain.userInfo.repository.UserInfoRepository
import com.ssafy.domain.userInfo.usercase.GetUserInfoUseCase
import com.ssafy.domain.userInfo.usercase.PostUserInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserInfoModule {

    @Provides
    fun provideUserInfoDataSource() : UserInfoDataSource{
        return FakeUserInfoDataSourceImpl()// 이거 교체 가능
    }

    @Provides
    fun provideUserInfoRepo(remote : UserInfoDataSource) : UserInfoRepository{
        return UserInfoRepositoryImpl(remote)
    }

    @Provides
    fun provideGetUserInfoUseCase(repo : UserInfoRepository) : GetUserInfoUseCase{
        return GetUserInfoUseCase(repo)
    }

    @Provides
    fun providePostUserInfoUseCase(repo : UserInfoRepository) : PostUserInfoUseCase {
        return PostUserInfoUseCase(repo)
    }




}