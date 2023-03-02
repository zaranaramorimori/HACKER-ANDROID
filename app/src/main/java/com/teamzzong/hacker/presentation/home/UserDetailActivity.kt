package com.teamzzong.hacker.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.ActivityUserDetailBinding
import com.teamzzong.hacker.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UserDetailActivity :
    BindingActivity<ActivityUserDetailBinding>(R.layout.activity_user_detail) {
    private val viewModel by viewModels<UserDetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnBack.setOnClickListener { finish() }
        viewModel.userDetailState
            .flowWithLifecycle(this.lifecycle)
            .onEach { detailProfile ->
                detailProfile?.let {
                    binding.txtDetailName.text = it.user.nickname
                    binding.txtDetailUserId.text = it.user.username
                    binding.txtDetailCommitCount.text = "${it.user.hairCount}가닥"
                    binding.imgDetailUserFace.load(it.face)
                    binding.imgDetailUserHair.load(it.head)
                }
            }.launchIn(this.lifecycleScope)
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, UserDetailActivity::class.java)
    }
}