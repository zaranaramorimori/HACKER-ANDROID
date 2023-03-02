package com.teamzzong.hacker.data.model.response.user


import com.teamzzong.hacker.domain.entity.user.UserDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserDetail(
    @SerialName("couponCount")
    val couponCount: Int,
    @SerialName("head")
    val head: String,
    @SerialName("face")
    val face: String,
    @SerialName("isMyFriend")
    val isMyFriend: Boolean,
    @SerialName("user")
    val user: User
) {
    @Serializable
    data class User(
        @SerialName("hairCount")
        val hairCount: Int,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("username")
        val username: String
    )

    fun toEntity() = UserDetail(
        isMyFriend = isMyFriend,
        userName = user.username,
        nickName = user.nickname,
        hairCount = user.hairCount,
        headImageUrl = head,
        faceImageUrl = face,
        couponCount = couponCount
    )
}
