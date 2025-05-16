package com.ssafy.di

import com.ssafy.data.community.api.CommentApiService
import com.ssafy.data.community.provider.CommentRemoteDataResource
import com.ssafy.data.community.provider.CommentRemoteDataResourceImpl
import com.ssafy.data.community.provider.CommunityRemoteDataSource
import com.ssafy.data.community.repository.CommentRepositoryImpl

import com.ssafy.data.community.repository.CommunityRepositoryImpl
import com.ssafy.domain.community.repository.CommentRepository
import com.ssafy.domain.community.repository.CommunityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CommentModule {

    @Provides
    fun provideCommentRemoteDataSource(api : CommentApiService): CommentRemoteDataResource {
        return CommentRemoteDataResourceImpl(api)
    }

    @Provides
    fun provideCommentRepository(remote : CommentRemoteDataResource): CommentRepository {
        return CommentRepositoryImpl(remote)
    }
}