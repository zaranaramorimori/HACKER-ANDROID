package com.teamzzong.hacker.data.model.response.friend

import com.teamzzong.hacker.domain.entity.user.GithubUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchFriend(
    val id: Int,
    @SerialName("username") val userName: String,
    @SerialName("nickname") val nickName: String,
    val profileImage: String,
    val isFriend: Boolean
) {
    fun toEntity() = GithubUser(id, userName, nickName, profileImage, isFriend)
}
