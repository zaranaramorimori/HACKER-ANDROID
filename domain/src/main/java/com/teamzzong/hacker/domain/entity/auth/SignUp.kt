package com.teamzzong.hacker.domain.entity.auth

data class SignUp(
    val social: String,
    val uuid: String,
    val userName: String,
    val nickName: String
)
