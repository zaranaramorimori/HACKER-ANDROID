package com.teamzzong.hacker.presentation.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamzzong.hacker.presentation.onboarding.content.OnboardingFragment

class OnboardingAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = ONBOARDING_COUNT

    override fun createFragment(position: Int): Fragment {
        return OnboardingFragment.newInstance(position)
    }

    companion object {
        private const val ONBOARDING_COUNT = 5
    }
}