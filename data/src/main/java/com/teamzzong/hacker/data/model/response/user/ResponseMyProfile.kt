package com.teamzzong.hacker.data.model.response.user

import com.teamzzong.hacker.domain.entity.user.Coupon
import com.teamzzong.hacker.domain.entity.user.Profile
import com.teamzzong.hacker.domain.entity.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyProfile(
    @SerialName("coupon")
    val coupon: ResponseCoupon,
    @SerialName("head")
    val head: String,
    @SerialName("face")
    val face: String,
    @SerialName("user")
    val user: ResponseUser,
    @SerialName("isAlertExist")
    val isAlertExist: Boolean
) {
    @Serializable
    data class ResponseUser(
        @SerialName("nickname")
        val nickname: String
    ) {
        fun toEntity() = User(nickname)
    }

    @Serializable
    data class ResponseCoupon(
        @SerialName("couponCommit")
        val couponCommit: Int,
        @SerialName("couponCount")
        val couponCount: Int,
        @SerialName("todayCommit")
        val todayCommit: Int
    ) {
        fun toEntity() = Coupon(todayCommit, couponCommit, couponCount)
    }

    fun toEntity() = Profile(
        user = user.toEntity(),
        head = head,
        face = face,
        coupon = coupon.toEntity(),
        isAlertExist = isAlertExist
    )
}
