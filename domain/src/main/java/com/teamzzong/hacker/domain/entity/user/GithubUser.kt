package com.teamzzong.hacker.domain.entity.user

data class GithubUser(
    val id: Int,
    val userName: String,
    val nickName: String,
    val profileImageUrl: String,
    val isFriend: Boolean
)
