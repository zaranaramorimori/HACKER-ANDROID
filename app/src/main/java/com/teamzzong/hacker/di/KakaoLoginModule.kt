package com.teamzzong.hacker.di

import com.teamzzong.hacker.data.repository.KakaoAuthInteractorImpl
import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object KakaoLoginModule {
    @Provides
    @ActivityScoped
    fun provideKakaoAuthInteractor(
        interactor: KakaoAuthInteractorImpl
    ): KakaoAuthInteractor = interactor
}