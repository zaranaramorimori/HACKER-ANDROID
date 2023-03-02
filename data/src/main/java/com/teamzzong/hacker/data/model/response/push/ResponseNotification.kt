package com.teamzzong.hacker.data.model.response.push

import com.teamzzong.hacker.domain.entity.notification.Notification
import com.teamzzong.hacker.shared.datetime.LocalDateSerializer
import kotlinx.datetime.LocalDate

@kotlinx.serialization.Serializable
data class ResponseNotification(
    val logs: List<Log>
) {
    @kotlinx.serialization.Serializable
    data class Log(
        val content: List<String>,
        @kotlinx.serialization.Serializable(with = LocalDateSerializer::class)
        val date: LocalDate
    ) {
        fun toEntity() = Notification(date, content)
    }

    fun toEntity() = logs.map { it.toEntity() }
}
