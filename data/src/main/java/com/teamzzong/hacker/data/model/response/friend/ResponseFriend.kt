package com.teamzzong.hacker.data.model.response.friend

import com.teamzzong.hacker.domain.entity.friend.Friend

@kotlinx.serialization.Serializable
data class ResponseFriend(
    val id: Int,
    val nickname: String,
    val head: String,
    val face: String
) {
    fun toEntity() = Friend(
        userId = id,
        nickName = nickname,
        headImageUrl = head,
        faceImageUrl = face
    )
}
