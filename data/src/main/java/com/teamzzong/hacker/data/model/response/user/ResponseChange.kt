package com.teamzzong.hacker.data.model.response.user

import com.teamzzong.hacker.domain.entity.user.Change
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseChange(
    val id: Int,
    @SerialName("nickname")
    val nickName: String
) {
    fun toChange(): Change {
        return Change(id, nickName)
    }
}