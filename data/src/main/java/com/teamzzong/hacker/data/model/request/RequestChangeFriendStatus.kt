package com.teamzzong.hacker.data.model.request

@kotlinx.serialization.Serializable
data class RequestChangeFriendStatus(
    val friendId: Int
)

fun Int.toRequestChangeFriendStatus() = RequestChangeFriendStatus(this)
