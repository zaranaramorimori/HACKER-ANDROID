package com.teamzzong.hacker.data.model.request

import com.teamzzong.hacker.domain.entity.auth.SignUp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignUp(
    val social: String,
    val uuid: String,
    @SerialName("username")
    val userName: String,
    @SerialName("nickname")
    val nickName: String
)

fun SignUp.toRequest(): RequestSignUp {
    return RequestSignUp(social, uuid, userName, nickName)
}