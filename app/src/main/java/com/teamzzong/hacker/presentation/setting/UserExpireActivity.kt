package com.teamzzong.hacker.presentation.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.processphoenix.ProcessPhoenix
import com.teamzzong.hacker.auth.login.LoginActivity
import com.teamzzong.hacker.data.remote.UserService
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.domain.usecase.auth.KakaoExpireUseCase
import com.teamzzong.hacker.ui.lifecycle.launchInStarted
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserExpireActivity : AppCompatActivity() {
    @Inject
    lateinit var dataStore: HackerDataStore

    @Inject
    lateinit var kakaoExpireUseCase: KakaoExpireUseCase

    @Inject
    lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserExpireScreen(
                onBack = { finish() },
                onPressExpire = {
                    SettingDialog.newInstance(
                        object : ItemClickListener {
                            override val title: String
                                get() = "정말로 탈퇴하시겠어요?"

                            override fun onConfirm() {
                                launchInStarted {
                                    userService.expireUser()
                                    kakaoExpireUseCase()
                                    dataStore.clear()
                                    ProcessPhoenix.triggerRebirth(
                                        this@UserExpireActivity,
                                        LoginActivity.getIntent(this@UserExpireActivity)
                                    )
                                }
                            }
                        }
                    ).show(supportFragmentManager, "ExpireDialog")
                }
            )
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, UserExpireActivity::class.java)
    }
}
