package com.teamzzong.hacker.data.model.response.user

import com.teamzzong.hacker.domain.entity.user.DetailProfile
import com.teamzzong.hacker.domain.entity.user.DetailUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDetailProfile(
    @SerialName("isMyFriend")
    val isMyFriend: Boolean?,
    @SerialName("user")
    val user: ResponseUser,
    @SerialName("head")
    val head: String,
    @SerialName("face")
    val face: String,
    @SerialName("couponCount")
    val couponCount: Int? = null
) {
    @Serializable
    data class ResponseUser(
        @SerialName("username")
        val username: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("hairCount")
        val hairCount: Int
    ) {
        fun toEntity() = DetailUser(username, nickname, hairCount)
    }

    fun toEntity() = DetailProfile(isMyFriend, user.toEntity(), head, face, couponCount)
}
