package com.teamzzong.hacker.data.model.response.battle

import com.teamzzong.hacker.domain.entity.battle.Season
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSeason(
    val seasons: List<Season>
) {
    @Serializable
    data class Season(
        val seasonId: Int,
        val imageUrl: String,
        val agency: String,
        val title: String,
        val duration: String
    )
}

fun ResponseSeason.toSeason(): List<Season> {
    return seasons.map {
        Season(
            it.seasonId,
            it.imageUrl,
            it.agency,
            it.title,
            it.duration
        )
    }
}