package com.teamzzong.hacker.navigator

import android.content.Context
import android.content.Intent
import com.teamzzong.hacker.presentation.HomeActivity
import com.teamzzong.hacker.presentation.onboarding.OnboardingActivity
import com.teamzzong.hacker.shared.AppNavigator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppNavigator {
    override fun navigateToHome() = Intent(context, HomeActivity::class.java)
        .setFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        )

    override fun navigateToOnboarding(): Intent = Intent(context, OnboardingActivity::class.java)
        .setFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        )
}