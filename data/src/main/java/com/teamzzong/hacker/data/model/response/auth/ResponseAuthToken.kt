package com.teamzzong.hacker.data.model.response.auth

import com.teamzzong.hacker.domain.entity.auth.AuthToken
import kotlinx.serialization.Serializable

@Serializable
data class ResponseAuthToken(
    val accessToken: String,
    val refreshToken: String
) {
    fun toAuthToken(): AuthToken {
        return AuthToken(accessToken, refreshToken)
    }
}
