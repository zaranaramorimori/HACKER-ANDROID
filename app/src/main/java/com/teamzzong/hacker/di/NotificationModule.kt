package com.teamzzong.hacker.di

import com.teamzzong.hacker.data.remote.NotificationService
import com.teamzzong.hacker.data.repository.NotificationRepositoryImpl
import com.teamzzong.hacker.domain.repository.notification.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    @Singleton
    fun provideNotificationService(retrofit: Retrofit): NotificationService =
        retrofit.create(NotificationService::class.java)

    @Provides
    @Singleton
    fun provideNotificationRepository(
        repository: NotificationRepositoryImpl
    ): NotificationRepository = repository
}
