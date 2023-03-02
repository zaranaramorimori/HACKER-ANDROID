package com.teamzzong.hacker.di

import com.teamzzong.hacker.data.datasource.AuthDataSource
import com.teamzzong.hacker.data.datasource.remote.AuthDataSourceImpl
import com.teamzzong.hacker.data.remote.AuthService
import com.teamzzong.hacker.data.repository.AuthRepositoryImpl
import com.teamzzong.hacker.domain.repository.auth.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideAuthDataSource(authDataSource: AuthDataSourceImpl): AuthDataSource = authDataSource

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository = authRepository
}
