package com.teamzzong.hacker.presentation.onboarding

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.ActivityOnboardingBinding
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.presentation.HomeActivity
import com.teamzzong.hacker.presentation.onboarding.adapter.OnboardingAdapter
import com.teamzzong.hacker.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity :
    BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    private lateinit var adapter: OnboardingAdapter

    @Inject
    lateinit var hackerDataStore: HackerDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        initViewPager()
        binding.txtOnboardingNext.setOnClickListener {
            when (val currentItem = binding.viewPager.currentItem) {
                in 0..3 -> binding.viewPager.currentItem = currentItem + 1
                else -> startHomeActivity()
            }
        }
        binding.txtOnboardingSkip.setOnClickListener {
            startHomeActivity()
        }
    }

    private fun initViewPager() {
        adapter = OnboardingAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    in 0..3 -> binding.txtOnboardingNext.setText(R.string.onboarding_next)
                    else -> binding.txtOnboardingNext.setText(R.string.onboarding_start)
                }
            }
        })
    }

    private fun startHomeActivity() {
        if (!hackerDataStore.isFirstAppVisit) {
            hackerDataStore.isFirstAppVisit = true
        }
        startActivity(HomeActivity.getIntentForFirstStart(this))
    }
}