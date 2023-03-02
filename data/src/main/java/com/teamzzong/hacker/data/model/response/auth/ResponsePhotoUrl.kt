package com.teamzzong.hacker.data.model.response.auth

import com.teamzzong.hacker.domain.entity.auth.PhotoUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePhotoUrl(
    @SerialName("username")
    val userName: String,
    val profileImage: String
) {
    fun toPhotoUrl() : PhotoUrl {
        return PhotoUrl(userName, profileImage)
    }
}
