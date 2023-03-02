package com.teamzzong.hacker.di

import com.teamzzong.hacker.data.remote.FriendService
import com.teamzzong.hacker.data.repository.FriendRepositoryImpl
import com.teamzzong.hacker.domain.repository.friend.FriendRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FriendModule {
    @Provides
    @Singleton
    fun provideFriendService(retrofit: Retrofit): FriendService =
        retrofit.create(FriendService::class.java)

    @Provides
    @Singleton
    fun provideFriendRepository(impl: FriendRepositoryImpl): FriendRepository = impl
}
