package com.teamzzong.hacker.presentation.home

import android.os.Bundle
import android.view.View
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.DialogHomeLoadingBinding
import com.teamzzong.hacker.ui.base.BindingDialogFragment

class HomeLoadingDialog :
    BindingDialogFragment<DialogHomeLoadingBinding>(R.layout.dialog_home_loading) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogBackground()
        dialog?.setCancelable(false)
    }

    private fun setDialogBackground() {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_transpart_dialog)
    }
}