package com.teamzzong.hacker.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teamzzong.hacker.notification.databinding.ActivityNotificationBinding
import com.teamzzong.hacker.ui.base.BindingActivity
import com.teamzzong.hacker.ui.view.Spacer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NotificationActivity :
    BindingActivity<ActivityNotificationBinding>(R.layout.activity_notification) {
    private val viewModel by viewModels<NotificationViewModel>()
    private val adapter = NotificationAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.listNotification.adapter = adapter
        binding.btnBack.setOnClickListener { finish() }
        binding.listNotification.addItemDecoration(Spacer { vertical { 8.dp } })
        viewModel.getNotifications()
        viewModel.notifications
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.imgNotificationEmpty.isVisible = it.isEmpty()
                binding.listNotification.isVisible = it.isNotEmpty()
                if (it.isNotEmpty()) adapter.replaceList(it)
            }.launchIn(lifecycleScope)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, NotificationActivity::class.java)
    }
}
