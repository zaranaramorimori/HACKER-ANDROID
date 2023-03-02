package com.teamzzong.hacker.data.model.response.battle

import com.teamzzong.hacker.domain.entity.battle.Base
import kotlinx.serialization.Serializable

@Serializable
data class ResponseEmpty(
    val status: Int,
    val success: Boolean,
    val message: String
) {
    fun toBase(): Base {
        return Base(status, success, message)
    }
}