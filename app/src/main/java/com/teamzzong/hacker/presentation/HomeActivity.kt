package com.teamzzong.hacker.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.teamzzong.hacker.R
import com.teamzzong.hacker.data.model.request.toDeviceToken
import com.teamzzong.hacker.data.remote.UserService
import com.teamzzong.hacker.databinding.ActivityHomeBinding
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.presentation.battle.season.BattleSeasonFragment
import com.teamzzong.hacker.presentation.gallery.MyPageFragment
import com.teamzzong.hacker.presentation.home.HomeFragment
import com.teamzzong.hacker.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    @Inject
    lateinit var hackerDataStore: HackerDataStore

    @Inject
    lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            runCatching {
                it.result
            }.onSuccess { token ->

                hackerDataStore.firebaseToken = token
                lifecycleScope.launch {
                    runCatching {
                        userService.registerFirebaseToken(
                            hackerDataStore.userToken,
                            hackerDataStore.firebaseToken.toDeviceToken()
                        )
                    }.onFailure(Timber::e)
                }
            }
        }
        binding.mainBnv.setOnItemSelectedListener {
            loadFragment(it.itemId)
            true
        }
        binding.mainBnv.selectedItemId = R.id.home
        binding.mainBnv.itemIconTintList = null
    }

    private fun loadFragment(@IdRes itemId: Int) {
        supportFragmentManager.commit {
            replace(R.id.main_container, createFragmentOf(itemId))
        }
    }

    private fun createFragmentOf(@IdRes itemId: Int) = when (itemId) {
        R.id.compete -> BattleSeasonFragment()
        R.id.home -> HomeFragment()
        R.id.mypage -> MyPageFragment()
        else -> throw IllegalStateException("Id: ${itemId}를 가진 Fragment는 존재하지 않습니다.")
    }

    companion object {
        fun getWidgetIntent(context: Context) = Intent(context, HomeActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        fun getIntentForFirstStart(context: Context) = Intent(context, HomeActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}
