package com.teamzzong.hacker.data.model.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ResponseInHouseAuth

@Serializable
@SerialName("signin")
data class ResponseLogin(
    val accessToken: String,
    val refreshToken: String,
    val id: Int,
    @SerialName("username") val userName: String,
    @SerialName("nickname") val nickName: String
) : ResponseInHouseAuth()

@Serializable
@SerialName("signup")
data class ResponseRegisterUser(
    val social: String,
    val uuid: String
) : ResponseInHouseAuth()
