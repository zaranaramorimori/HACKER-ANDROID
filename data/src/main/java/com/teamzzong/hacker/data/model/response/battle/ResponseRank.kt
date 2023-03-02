package com.teamzzong.hacker.data.model.response.battle

import com.teamzzong.hacker.domain.entity.battle.MyTeam
import com.teamzzong.hacker.domain.entity.battle.Rank
import com.teamzzong.hacker.domain.entity.battle.Teams
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRank(
    val myTeam: ResponseMyTeam,
    val teams: List<ResponseTeams>
) {
    fun toTeamRank(): Rank {
        return Rank(myTeam.toMyTeam(), teams.map { it.toTeams() })
    }
}

@Serializable
data class ResponseMyTeam(
    val teamId: Int,
    val rank: Int,
    val name: String,
    val commitCount: Int
) {
    fun toMyTeam(): MyTeam {
        return MyTeam(teamId, rank, name, commitCount)
    }
}

@Serializable
data class ResponseTeams(
    val teamId: Int,
    val rank: Int,
    val name: String,
    val commitCount: Int,
    val hairCount: Int,
    val head: String?,
    val face: String
) {
    fun toTeams(): Teams {
        return Teams(teamId, rank, name, commitCount, hairCount, head, face)
    }
}
