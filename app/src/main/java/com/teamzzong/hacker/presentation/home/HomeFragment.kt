package com.teamzzong.hacker.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.simform.refresh.SSPullToRefreshLayout
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.FragmentHomeBinding
import com.teamzzong.hacker.notification.NotificationActivity
import com.teamzzong.hacker.presentation.setting.SettingActivity
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private var loadingDialog: HomeLoadingDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingDialog()
        initView()
        subscribeData()
    }

    private fun showLoadingDialog() {
        loadingDialog = HomeLoadingDialog()
        loadingDialog?.show(parentFragmentManager, "LoadingDialog")
    }

    private fun initView() {
        binding.layoutRefresh.apply {
            setLottieAnimation("head_spin.json")
            setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
            setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
            setRefreshStyle(SSPullToRefreshLayout.RefreshStyle.NORMAL)
            setOnRefreshListener { viewModel.setHomeScreen() }
        }
        binding.imgHomeUserFace.setOnSingleClickListener {
            startActivity(UserDetailActivity.getIntent(requireContext()))
        }
        binding.btnHomeRefresh.setOnClickListener {
            it.isEnabled = false
            showLoadingDialog()
            viewModel.setHomeScreen()
        }
        binding.btnHomeNotification.setOnSingleClickListener {
            startActivity(NotificationActivity.getIntent(requireContext()))
        }
        binding.btnHomeSetting.setOnClickListener {
            startActivity(SettingActivity.getIntent(requireContext()))
        }
        binding.txtHomeChange.setOnClickListener {
            viewModel.exchangeCoupon()
        }
    }

    private fun subscribeData() {
        viewModel.userState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { profile ->
                profile?.let {
                    binding.txtHomeUser.text = it.user.name
                    binding.txtHomeCommitCount.text = "${it.coupon.todayCommit}/10"
                    binding.txtAttackCount.text = "x${it.coupon.couponCount}"
                    binding.imgHomeUserFace.load(it.face)
                    binding.imgHomeUserHair.load(it.head)
                    binding.btnHomeNotification.isSelected = it.isAlertExist
                    setExchangeButton(it.coupon.couponCommit)
                }
                binding.btnHomeRefresh.isEnabled = true
                loadingDialog?.dismiss()
                binding.layoutRefresh.setRefreshing(false)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setExchangeButton(couponCommit: Int) {
        if (couponCommit >= 10) {
            with(binding.txtHomeChange) {
                text = "교환하기 (${couponCommit / 10})"
                isVisible = true
                isClickable = true
            }
            binding.progressHomeExp.progress = 0f
        } else {
            with(binding.txtHomeChange) {
                isVisible = false
                isClickable = false
            }
            binding.progressHomeExp.progress = (couponCommit % 10) * 10.toFloat()
        }
    }
}
