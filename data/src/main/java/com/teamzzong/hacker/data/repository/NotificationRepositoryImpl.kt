package com.teamzzong.hacker.data.repository

import com.teamzzong.hacker.data.remote.NotificationService
import com.teamzzong.hacker.domain.entity.notification.Notification
import com.teamzzong.hacker.domain.repository.notification.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val service: NotificationService
) : NotificationRepository {
    override suspend fun getNotifications(): List<Notification> {
        return service.fetchNotifications().data.toEntity()
    }
}
