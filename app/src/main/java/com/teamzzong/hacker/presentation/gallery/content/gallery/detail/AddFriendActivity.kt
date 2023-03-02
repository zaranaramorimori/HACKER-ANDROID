package com.teamzzong.hacker.presentation.gallery.content.gallery.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.ActivityAddFriendBinding
import com.teamzzong.hacker.presentation.battle.attack.PullHairDialog
import com.teamzzong.hacker.ui.base.BindingActivity
import com.teamzzong.hacker.ui.context.toast
import com.teamzzong.hacker.ui.intent.intExtra
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AddFriendActivity : BindingActivity<ActivityAddFriendBinding>(R.layout.activity_add_friend) {
    private val userId by intExtra()
    private val viewModel by viewModels<AddFriendViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setUserDetailScreen(userId)
        initView()
        subscribeData()
    }

    private fun initView() {
        binding.btnBack.setOnClickListener { finish() }
        binding.btnFriendAdd.setOnSingleClickListener {
            viewModel.changeFriendStatusOf(userId)
        }
        binding.btnFriendAttack.setOnSingleClickListener {
            PullHairDialog().show(supportFragmentManager, "dialog")
            viewModel.attackUserOf(userId)
        }
    }

    private fun subscribeData() {
        viewModel.isAttackEnabled
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.btnFriendAttack.isEnabled = it
                binding.btnFriendAttack.isSelected = it
            }.launchIn(lifecycleScope)
        viewModel.isMyFriend
            .flowWithLifecycle(lifecycle)
            .onEach { binding.btnFriendAdd.isSelected = it }
            .launchIn(lifecycleScope)
        viewModel.userDetail
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.imgFriendAddUserHair.load(it.headImageUrl)
                binding.imgFriendAddUserFace.load(it.faceImageUrl)
                binding.txtFriendAddCount.text = "${it.hairCount}가닥"
                binding.txtFriendAddName.text = it.nickName
                binding.txtFriendNickname.text = it.userName
            }.launchIn(lifecycleScope)
        viewModel.toastMessage
            .flowWithLifecycle(lifecycle)
            .onEach { toast(it) }
            .launchIn(lifecycleScope)
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context, userId: Int) =
            Intent(context, AddFriendActivity::class.java)
                .putExtra("userId", userId)
    }
}
