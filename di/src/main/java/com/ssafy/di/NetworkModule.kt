package com.ssafy.di


import com.ssafy.data.auth.api.AuthApi
import com.ssafy.data.boiler.provider.BoilerApiService

import com.ssafy.data.community.api.CommentApiService
import com.ssafy.data.community.api.CommunityApiService
import com.ssafy.data.mypage.provider.MileageApiService
import com.ssafy.data.remote.interceptor.AuthorizationInterceptor
import com.ssafy.data.weather.provider.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://3.37.252.209/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideBoilerApiService(retrofit: Retrofit): BoilerApiService {
        return retrofit.create(BoilerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommunityApiService(retrofit: Retrofit) : CommunityApiService {
        return retrofit.create(CommunityApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentApiService(retrofit: Retrofit) :CommentApiService {
        return retrofit.create(CommentApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMileageApiService(retrofit: Retrofit): MileageApiService {
        return retrofit.create(MileageApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit) : WeatherApi{
        return retrofit.create(WeatherApi::class.java)
    }

}
