package com.ssafy.di

import com.ssafy.data.weather.provider.WeatherApi
import com.ssafy.data.weather.repository.WeatherRepositoryImpl
import com.ssafy.domain.weather.repository.WeatherRepository
import com.ssafy.domain.weather.usecase.GetAdviceUseCase
import com.ssafy.domain.weather.usecase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Provides
    fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    @Provides
    fun provideGetAdviceUseCase(repository: WeatherRepository): GetAdviceUseCase {
        return GetAdviceUseCase(repository)
    }
}
