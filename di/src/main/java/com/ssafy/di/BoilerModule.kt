package com.ssafy.di

import com.ssafy.data.boiler.provider.BoilerProvider
import com.ssafy.data.boiler.provider.MockBoilerProvider
import com.ssafy.data.boiler.repository.BoilerRepositoryImpl
import com.ssafy.domain.bolier.repository.BoilerRepository
import com.ssafy.domain.bolier.usecase.GetBoilerListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object BoilerModule {

    @Provides
    fun provideBoilerProvider(): BoilerProvider = MockBoilerProvider()

    @Provides
    fun provideBoilerRepository(
        provider: BoilerProvider
    ): BoilerRepository = BoilerRepositoryImpl(provider)

    @Provides
    fun provideGetBoilerListUseCase(repo: BoilerRepository): GetBoilerListUseCase =
        GetBoilerListUseCase(repo)
}
