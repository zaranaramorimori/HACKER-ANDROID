package com.teamzzong.hacker.auth.register

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
import com.teamzzong.hacker.auth.R
import com.teamzzong.hacker.auth.databinding.ActivityNickNameBinding
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.domain.entity.auth.SignUp
import com.teamzzong.hacker.shared.AppNavigator
import com.teamzzong.hacker.ui.base.BindingActivity
import com.teamzzong.hacker.ui.intent.stringExtra
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class NickNameActivity : BindingActivity<ActivityNickNameBinding>(R.layout.activity_nick_name) {
    @Inject
    lateinit var dataStore: HackerDataStore

    @Inject
    lateinit var navigator: AppNavigator
    private val userName by stringExtra()
    private val viewModel: NickNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        initEvent()
        observe()
    }

    private fun initEvent() {
        binding.ivClear.setOnClickListener {
            binding.editNickname.text.clear()
        }
        binding.ivStart.setOnSingleClickListener {
            val auth = SignUp(
                dataStore.social,
                dataStore.userUUID,
                userName.toString(),
                binding.editNickname.text.toString()
            )
            dataStore.hackerNickName = binding.editNickname.text.toString()
            viewModel.postSignUp(auth)
        }

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
    }

    private fun observe() {
        viewModel.isClickable.flowWithLifecycle(lifecycle)
            .onEach {
                if (it) binding.tvNicknameQuestion.text = "와 제법 멋진 이름이네요"
                else binding.tvNicknameQuestion.text = "새로운 닉네임을 알려주세요"

                binding.ivStart.apply {
                    isSelected = it
                    isClickable = it
                }
            }.launchIn(lifecycleScope)

        viewModel.checkNickName.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is NickNameViewModel.Event.Success -> {
                        startActivity(navigator.navigateToHome())
                    }
                    is NickNameViewModel.Event.Failure -> {
                        binding.tvNicknameQuestion.text = it.msg
                        binding.ivClear.setImageResource(R.drawable.ic_not_id)
                    }
                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
        fun getIntent(context: Context, userName: String) =
            Intent(context, NickNameActivity::class.java)
                .putExtra("userName", userName)
    }
}