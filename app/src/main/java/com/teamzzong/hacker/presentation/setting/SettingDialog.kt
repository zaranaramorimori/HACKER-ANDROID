package com.teamzzong.hacker.presentation.setting

import android.os.Bundle
import android.view.View
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.DialogLogoutBinding
import com.teamzzong.hacker.ui.base.BindingDialogFragment
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingDialog : BindingDialogFragment<DialogLogoutBinding>(R.layout.dialog_logout) {
    private var settingListener: ItemClickListener? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            txtLogoutTitle.text = settingListener?.title ?: "로그아웃할까요?"
            btnLogoutConfirm.setOnSingleClickListener { settingListener?.onConfirm() }
            btnLogoutDecline.setOnSingleClickListener { dismiss() }
        }
    }

    private fun setLogoutListener(listener: ItemClickListener) {
        settingListener = listener
    }

    override fun onDestroyView() {
        settingListener = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(itemClickListener: ItemClickListener) = SettingDialog().apply {
            setLogoutListener(itemClickListener)
        }
    }
}

interface ItemClickListener {
    val title: String
    fun onConfirm()
}