package com.teamzzong.hacker.domain.entity.user

data class Profile(
    val user: User,
    val head: String,
    val face: String,
    val coupon: Coupon,
    val isAlertExist: Boolean
)
