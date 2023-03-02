package com.teamzzong.hacker.presentation.onboarding.content

import android.os.Bundle
import android.view.View
import coil.load
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.FragmentOnboardingBinding
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.intent.intArgs

class OnboardingFragment :
    BindingFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {
    private val position by intArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val onboardingImage = when (position) {
            0 -> R.drawable.onboarding_1
            1 -> R.drawable.onboarding_2
            2 -> R.drawable.onboarding_3
            3 -> R.drawable.onboarding_4
            4 -> R.drawable.onboarding_5
            else -> throw IllegalArgumentException("$position is Invalid Position")
        }
        binding.ivOnboarding.load(onboardingImage)
    }

    companion object {
        fun newInstance(position: Int) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putInt("position", position)
                }
            }
    }
}