package com.teamzzong.hacker.presentation.battle.attack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.DialogPullHairBinding
import com.teamzzong.hacker.ui.base.BindingDialogFragment

class PullHairDialog : BindingDialogFragment<DialogPullHairBinding>(R.layout.dialog_pull_hair) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setDialogBackground()
    }

    private fun initView() {
        binding.lottie.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dismiss()
            }
        })
    }

    private fun setDialogBackground() {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_transpart_dialog)
    }
}