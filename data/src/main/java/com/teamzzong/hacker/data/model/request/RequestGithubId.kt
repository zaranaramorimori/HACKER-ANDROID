package com.teamzzong.hacker.data.model.request

import com.teamzzong.hacker.domain.entity.auth.GithubId
import kotlinx.serialization.Serializable

@Serializable
data class RequestGithubId(
    val id: String
)

fun GithubId.toRequest(): RequestGithubId {
    return RequestGithubId(id)
}
