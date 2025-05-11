package com.ssafy.di

import com.ssafy.data.community.api.CommunityApiService
import com.ssafy.data.community.provider.CommunityRemoteDataSource
import com.ssafy.data.community.provider.CommunityRemoteDataSourceImpl
import com.ssafy.data.community.repository.CommunityRepositoryImpl

import com.ssafy.domain.community.repository.CommunityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CommunityModule {

    @Provides
    fun provideCommunityRemoteDataSource(api : CommunityApiService): CommunityRemoteDataSource {
        return CommunityRemoteDataSourceImpl(api)
    }

    @Provides
    fun provideCommunityRepository(remote : CommunityRemoteDataSource): CommunityRepository {
        return CommunityRepositoryImpl(remote)
    }

}