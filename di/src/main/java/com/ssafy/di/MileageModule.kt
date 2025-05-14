package com.ssafy.di

import com.ssafy.data.mypage.provider.MileageApiService
import com.ssafy.data.mypage.repository.MileageRepositoryImpl
import com.ssafy.domain.mypage.repository.MileageRepository
import com.ssafy.domain.mypage.usecase.CheckAttendanceUseCase
import com.ssafy.domain.mypage.usecase.GetMileageHistoryUseCase
import com.ssafy.domain.mypage.usecase.GetMileageStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MileageModule {

    @Provides
    @Singleton
    fun provideMileageApiService(): MileageApiService {
        return Retrofit.Builder()
            .baseUrl("http://3.34.3.125")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MileageApiService::class.java)
    }

    @Provides
    fun provideMileageRepository(apiService: MileageApiService): MileageRepository {
        return MileageRepositoryImpl(apiService)
    }

    @Provides
    fun provideGetMileageStatusUseCase(repository: MileageRepository): GetMileageStatusUseCase {
        return GetMileageStatusUseCase(repository)
    }

    @Provides
    fun provideGetMileageHistoryUseCase(repository: MileageRepository): GetMileageHistoryUseCase {
        return GetMileageHistoryUseCase(repository)
    }

    @Provides
    fun provideCheckAttendanceUseCase(repository: MileageRepository): CheckAttendanceUseCase {
        return CheckAttendanceUseCase(repository)
    }
}
