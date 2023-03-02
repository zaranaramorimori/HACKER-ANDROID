package com.teamzzong.hacker.domain.entity.user

data class Rank(
    val userId: Int,
    val rank: Int,
    val nickname: String,
    val commitCount: Int
)
