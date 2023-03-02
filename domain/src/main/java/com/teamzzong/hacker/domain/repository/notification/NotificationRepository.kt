package com.teamzzong.hacker.domain.repository.notification

import com.teamzzong.hacker.domain.entity.notification.Notification

interface NotificationRepository {
    suspend fun getNotifications(): List<Notification>
}
