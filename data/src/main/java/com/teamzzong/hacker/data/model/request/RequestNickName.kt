package com.teamzzong.hacker.data.model.request

import com.teamzzong.hacker.domain.entity.user.NickName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestNickName(
    @SerialName("nickname")
    val nickName: String
)

fun NickName.toRequest(): RequestNickName {
    return RequestNickName(nickName)
}