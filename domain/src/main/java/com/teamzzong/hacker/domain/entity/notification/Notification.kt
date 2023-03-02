package com.teamzzong.hacker.domain.entity.notification

import kotlinx.datetime.LocalDate

data class Notification(
    val date: LocalDate,
    val content: List<String>
)
