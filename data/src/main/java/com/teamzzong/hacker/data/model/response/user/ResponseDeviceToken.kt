package com.teamzzong.hacker.data.model.response.user

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class ResponseDeviceToken(
    val deviceToken: String
)
