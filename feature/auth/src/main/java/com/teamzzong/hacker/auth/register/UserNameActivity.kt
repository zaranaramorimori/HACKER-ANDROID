package com.teamzzong.hacker.auth.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teamzzong.hacker.auth.R
import com.teamzzong.hacker.auth.databinding.ActivityUserNameBinding
import com.teamzzong.hacker.ui.base.BindingActivity
import com.teamzzong.hacker.ui.context.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UserNameActivity : BindingActivity<ActivityUserNameBinding>(R.layout.activity_user_name) {
    private val viewModel: UserNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        initEvent()
        observe()
    }

    private fun initEvent() {
        binding.ivClear.setOnClickListener {
            binding.editUserName.text.clear()
        }

        binding.ivNext.setOnClickListener {
            viewModel.postGithubId(viewModel.name.value)
        }

        binding.editUserName.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.ivNext.isClickable) {
                    binding.ivNext.performClick()
                }
                return@OnEditorActionListener true
            }
            false
        })

        binding.editUserName.setOnFocusChangeListener { _, hasFocus ->
            binding.ivClear.isVisible = hasFocus
        }
    }

    private fun observe() {
        viewModel.isClickable.flowWithLifecycle(lifecycle)
            .onEach {
                binding.ivNext.apply {
                    isSelected = it
                    isClickable = it
                }
            }.launchIn(lifecycleScope)

        viewModel.checkId.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is UserNameViewModel.Event.Success -> {
                        val dialog =
                            UserNameDialog.newInstance(viewModel.name.value, it.url.profileImage)
                        dialog.show(supportFragmentManager, "dialog")
                    }
                    is UserNameViewModel.Event.Failure -> {
                        toast(it.msg)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, UserNameActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}
