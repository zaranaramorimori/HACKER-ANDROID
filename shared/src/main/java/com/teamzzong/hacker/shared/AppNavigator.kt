package com.teamzzong.hacker.shared

import android.content.Intent

interface AppNavigator {
    fun navigateToHome(): Intent
    fun navigateToOnboarding(): Intent
}
