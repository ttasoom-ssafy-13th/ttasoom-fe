package com.ssafy.di

import com.ssafy.data.boiler.repository.BoilerRepositoryImpl
import com.ssafy.data.boiler.provider.BoilerRemoteDataSource
import com.ssafy.data.boiler.provider.FakeBoilerRemoteDataSourceImpl
import com.ssafy.domain.boiler.repository.BoilerRepository
import com.ssafy.domain.boiler.usecase.GetBoilerListUseCase
import com.ssafy.domain.boiler.usecase.GetFilterBoilerListUseCase
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

    @Provides
    fun provideGetFilterBoilerListUseCase(repo: BoilerRepository): GetFilterBoilerListUseCase {
        return GetFilterBoilerListUseCase(repo)
    }
}
