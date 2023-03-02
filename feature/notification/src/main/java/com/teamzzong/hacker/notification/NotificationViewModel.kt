package com.teamzzong.hacker.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.notification.Notification
import com.teamzzong.hacker.domain.repository.notification.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {
    private val _notifications: MutableStateFlow<List<NotificationUiState>?> =
        MutableStateFlow(null)
    val notifications = _notifications.asStateFlow().filterNotNull()

    fun getNotifications() {
        viewModelScope.launch {
            runCatching { repository.getNotifications() }
                .onSuccess {
                    val notificationUiData = parseNotifications(it)
                    _notifications.value = notificationUiData
                }.onFailure(Timber::e)
        }
    }

    private fun parseNotifications(items: List<Notification>): List<NotificationUiState> {
        if (items.isEmpty()) return emptyList()
        val notificationList: MutableList<NotificationUiState> = mutableListOf()
        notificationList.add(NotificationUiState.Head(items.first().date))
        items.forEachIndexed { index, item ->
            item.content.forEach {
                notificationList.add(NotificationUiState.Item(NotificationUiData(item.date, it)))
            }
            if (index != items.lastIndex) {
                if (item.date != items[index + 1].date) {
                    notificationList.add(NotificationUiState.Head(items[index + 1].date))
                }
            }
        }
        return notificationList
    }

    data class NotificationUiData(
        val date: LocalDate,
        val content: String
    )

    sealed class NotificationUiState {
        data class Head(val date: LocalDate) : NotificationUiState()
        data class Item(val notification: NotificationUiData) : NotificationUiState()
    }
}
