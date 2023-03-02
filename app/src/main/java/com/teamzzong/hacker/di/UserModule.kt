package com.teamzzong.hacker.di

import com.teamzzong.hacker.data.datasource.UserDataSource
import com.teamzzong.hacker.data.datasource.remote.UserDataSourceImpl
import com.teamzzong.hacker.data.remote.UserService
import com.teamzzong.hacker.data.repository.UserRepositoryImpl
import com.teamzzong.hacker.domain.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(repository: UserRepositoryImpl): UserRepository = repository

    @Singleton
    @Provides
    fun provideUserDataSource(userDataSource: UserDataSourceImpl): UserDataSource = userDataSource
}
