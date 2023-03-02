package com.teamzzong.hacker.domain.entity.auth

data class AuthToken(
    val accessToken: String,
    val refreshToken: String
)