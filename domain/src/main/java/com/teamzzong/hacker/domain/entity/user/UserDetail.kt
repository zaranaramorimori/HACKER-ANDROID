package com.teamzzong.hacker.domain.entity.user

data class UserDetail(
    val isMyFriend: Boolean,
    val userName: String,
    val nickName: String,
    val hairCount: Int,
    val headImageUrl: String,
    val faceImageUrl: String,
    val couponCount: Int
)
