package com.teamzzong.hacker.presentation.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.jakewharton.processphoenix.ProcessPhoenix
import com.teamzzong.hacker.R
import com.teamzzong.hacker.data.remote.UserService
import com.teamzzong.hacker.databinding.ActivitySettingBinding
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.domain.usecase.auth.KakaoLogoutUseCase
import com.teamzzong.hacker.shared.WebViewActivity
import com.teamzzong.hacker.ui.base.BindingActivity
import com.teamzzong.hacker.ui.lifecycle.launchInStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BindingActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    @Inject
    lateinit var logoutUseCase: KakaoLogoutUseCase

    @Inject
    lateinit var dataStore: HackerDataStore

    @Inject
    lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnBack.setOnClickListener { finish() }
        binding.nicknameChangeContainer.setOnClickListener {
            startActivity(ChangeNameActivity.getIntent(this))
        }
        binding.serviceTermContainer.setOnClickListener {
            startActivity(
                WebViewActivity.getIntent(
                    this, "https://haircommit.notion.site/18b1ba8bb79840f4ba75c19331ca339d"
                )
            )
        }
        binding.personalInfoContainer.setOnClickListener {
            startActivity(
                WebViewActivity.getIntent(
                    this, "https://haircommit.notion.site/4fdc6813063647c0a98f2042d8f1cd87"
                )
            )
        }
        binding.opensourceContainer.setOnClickListener {
            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        }
        binding.makersContainer.setOnClickListener {
            startActivity(
                MakersWebViewActivity.getIntent(
                    this, "https://hacker-webview.vercel.app/members"
                )
            )
        }
        binding.logoutContainer.setOnClickListener {
            SettingDialog.newInstance(object : ItemClickListener {
                override val title: String
                    get() = "로그아웃할까요?"

                override fun onConfirm() {
                    launchInStarted {
                        logoutUseCase()
                        dataStore.clear()
                        ProcessPhoenix.triggerRebirth(this@SettingActivity)
                    }
                }
            }).show(supportFragmentManager, "ExpireDialog")
        }
        binding.expireContainer.setOnClickListener {
            lifecycleScope.launch {
                startActivity(UserExpireActivity.getIntent(this@SettingActivity))
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SettingActivity::class.java)
    }
}