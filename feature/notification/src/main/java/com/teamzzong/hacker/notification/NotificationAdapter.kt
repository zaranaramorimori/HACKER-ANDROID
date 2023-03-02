package com.teamzzong.hacker.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.teamzzong.hacker.notification.databinding.ItemNotificationBinding
import com.teamzzong.hacker.notification.databinding.ItemNotificationDateBinding

class NotificationAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var notificationList: List<NotificationViewModel.NotificationUiState> = emptyList()

    class HeaderViewHolder(
        private val binding: ItemNotificationDateBinding
    ) : ViewHolder(binding.root) {
        fun onBind(item: NotificationViewModel.NotificationUiState.Head) {
            binding.txtNotificationDate.text = item.date.toString()
        }
    }

    class NotificationViewHolder(
        private val binding: ItemNotificationBinding
    ) : ViewHolder(binding.root) {
        fun onBind(item: NotificationViewModel.NotificationUiState.Item) {
            binding.txtNotificationContent.text = item.notification.content
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (notificationList[position]) {
            is NotificationViewModel.NotificationUiState.Head -> HEAD
            is NotificationViewModel.NotificationUiState.Item -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            HEAD -> {
                val binding = ItemNotificationDateBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            ITEM -> {
                val binding = ItemNotificationBinding.inflate(inflater, parent, false)
                NotificationViewHolder(binding)
            }
            else -> throw IllegalStateException("There's no viewType in this adapter. ViewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                require(notificationList[position] is NotificationViewModel.NotificationUiState.Head)
                holder.onBind(notificationList[position] as NotificationViewModel.NotificationUiState.Head)
            }
            is NotificationViewHolder -> {
                require(notificationList[position] is NotificationViewModel.NotificationUiState.Item)
                holder.onBind(notificationList[position] as NotificationViewModel.NotificationUiState.Item)
            }
        }
    }

    override fun getItemCount() = notificationList.size

    fun replaceList(items: List<NotificationViewModel.NotificationUiState>) {
        notificationList = items.toList()
        notifyDataSetChanged()
    }

    companion object {
        private const val HEAD = 1000
        private const val ITEM = 1001
    }
}
