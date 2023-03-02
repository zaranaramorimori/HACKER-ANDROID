package com.teamzzong.hacker.data.model.response

@kotlinx.serialization.Serializable
data class EmptyResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
)
