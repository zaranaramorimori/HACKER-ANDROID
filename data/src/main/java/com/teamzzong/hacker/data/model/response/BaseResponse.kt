package com.teamzzong.hacker.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T
)
