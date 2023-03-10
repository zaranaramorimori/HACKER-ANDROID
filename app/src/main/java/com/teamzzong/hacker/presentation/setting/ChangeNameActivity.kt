package com.teamzzong.hacker.presentation.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.ActivityChaneNameBinding
import com.teamzzong.hacker.domain.entity.user.NickName
import com.teamzzong.hacker.ui.base.BindingActivity
import com.teamzzong.hacker.ui.context.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ChangeNameActivity : BindingActivity<ActivityChaneNameBinding>(R.layout.activity_chane_name) {
    private val viewModel by viewModels<ChangeNameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        initEvent()
        observe()
    }

    private fun initEvent() {
        binding.editNickname.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.ivStart.isClickable)
                    binding.ivStart.performClick()
                return@OnEditorActionListener true
            }
            false
        })

        binding.editNickname.setOnFocusChangeListener { _, hasFocus ->
            binding.ivClear.isVisible = hasFocus
        }

        binding.editNickname.doAfterTextChanged {
            binding.tvCount.text = "${it?.length}/6"
            binding.ivClear.setImageResource(R.drawable.ic_x_white)
        }

        binding.ivStart.setOnClickListener {
            val nickName = NickName(binding.editNickname.text.toString())
            viewModel.changeNickName(nickName)
        }
    }

    private fun observe() {
        viewModel.isClickable.flowWithLifecycle(lifecycle)
            .onEach {
                if (it) binding.tvNicknameQuestion.text = "?????????! ?????? ?????? ???????????????"
                else binding.tvNicknameQuestion.text = "????????? ???????????? ???????????????"
                binding.ivStart.apply {
                    isSelected = it
                    isClickable = it
                }
            }.launchIn(lifecycleScope)

        viewModel.changeResult.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is ChangeNameViewModel.Event.SuccessChange -> {
                        toast(it.msg)
                        finish()
                    }
                    is ChangeNameViewModel.Event.FailChange -> {
                        binding.tvNicknameQuestion.text = it.msg
                        binding.ivClear.setImageResource(R.drawable.ic_not_id)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, ChangeNameActivity::class.java)
    }
}