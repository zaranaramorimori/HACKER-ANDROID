package com.teamzzong.hacker.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.teamzzong.hacker.auth.register.UserNameActivity
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor
import com.teamzzong.hacker.shared.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var hackerDataStore: HackerDataStore

    @Inject
    lateinit var kakaoAuthInteractor: KakaoAuthInteractor

    @Inject
    lateinit var appNavigator: AppNavigator

    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    private val remoteConfig by lazy { Firebase.remoteConfig }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        navigateLogin()
    }

    private fun navigateLogin() {
        setContent {
            LoginScreen(
                hiltViewModel(),
                kakaoAuthInteractor,
                {
                    if (hackerDataStore.isFirstAppVisit) {
                        startActivity(appNavigator.navigateToHome())
                    } else {
                        startActivity(appNavigator.navigateToOnboarding())
                    }
                }
            ) {
                if (hackerDataStore.isFirstAppVisit) {
                    startActivity(UserNameActivity.getIntent(this))
                } else {
                    startActivity(appNavigator.navigateToOnboarding())
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}
