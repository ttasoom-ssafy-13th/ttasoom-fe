package com.ssafy.di

import BoilerRepositoryImpl
import com.ssafy.data.boiler.provider.BoilerRemoteDataSource
import com.ssafy.data.boiler.provider.FakeBoilerRemoteDataSourceImpl // 이거 꼭 추가!
import com.ssafy.domain.boiler.repository.BoilerRepository
import com.ssafy.domain.boiler.usecase.GetBoilerListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object BoilerModule {

    @Provides
    fun provideBoilerRemoteDataSource(): BoilerRemoteDataSource {
        return FakeBoilerRemoteDataSourceImpl()
    }

    @Provides
    fun provideBoilerRepository(remote: BoilerRemoteDataSource): BoilerRepository {
        return BoilerRepositoryImpl(remote)
    }

    @Provides
    fun provideGetBoilerListUseCase(repo: BoilerRepository): GetBoilerListUseCase {
        return GetBoilerListUseCase(repo)
    }
}
