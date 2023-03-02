package com.teamzzong.hacker.domain.entity.user

data class DetailProfile(
    val isMyFriend: Boolean?,
    val user: DetailUser,
    val head: String,
    val face: String,
    val couponCount: Int?
)
