package com.teamzzong.hacker.data.model.request

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class RequestDeviceToken(
    val deviceToken: String
)

fun String.toDeviceToken() = RequestDeviceToken(this)
