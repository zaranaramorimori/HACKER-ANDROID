package com.teamzzong.hacker.auth.register

import android.graphics.Color
import android.os.Bundle
import android.view.View
import coil.load
import com.teamzzong.hacker.auth.R
import com.teamzzong.hacker.auth.databinding.DialogUserNameBinding
import com.teamzzong.hacker.ui.base.BindingDialogFragment
import com.teamzzong.hacker.ui.intent.stringArgs

class UserNameDialog : BindingDialogFragment<DialogUserNameBinding>(R.layout.dialog_user_name) {
    private val photoUrl by stringArgs()
    private val githubId by stringArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        setBorderStyle()
        setDialogBackground()
    }

    private fun initView() {
        binding.ivYes.setOnClickListener {
            startActivity(NickNameActivity.getIntent(requireContext(), githubId))
            dismiss()
            requireActivity().finish()
        }

        binding.ivNo.setOnClickListener {
            dismiss()
        }
    }

    private fun initData() {
        binding.tvGithubName.text = githubId
        binding.ivProfile.load(photoUrl)

    }

    private fun setDialogBackground() {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
    }

    private fun setBorderStyle() {
        binding.ivProfile.setBorderStyle(10f, Color.BLACK)
    }

    companion object {
        @JvmStatic
        fun newInstance(githubId: String, photoUrl: String) =
            UserNameDialog().apply {
                arguments = Bundle().apply {
                    putString("githubId", githubId)
                    putString("photoUrl", photoUrl)
                }
            }
    }
}
