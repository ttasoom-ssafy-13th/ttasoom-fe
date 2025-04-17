package com.ssafy.di

import com.ssafy.data.auth.provider.AuthProvider
import com.ssafy.data.auth.provider.EmailAuthProvider
import com.ssafy.data.auth.provider.GoogleAuthProvider
import com.ssafy.data.auth.repository.AuthRepositoryImpl
import com.ssafy.domain.model.AuthProviderType
import com.ssafy.domain.repository.AuthRepository
import com.ssafy.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideAuthRepository(
        email: EmailAuthProvider,
        google: GoogleAuthProvider
    ): AuthRepository = AuthRepositoryImpl(
        providers = mapOf(
            AuthProviderType.EMAIL to email as AuthProvider,
            AuthProviderType.GOOGLE to google as AuthProvider
        )
    )

    @Provides
    fun provideLoginUseCase(repo: AuthRepository): LoginUseCase =
        LoginUseCase(repo)
}
