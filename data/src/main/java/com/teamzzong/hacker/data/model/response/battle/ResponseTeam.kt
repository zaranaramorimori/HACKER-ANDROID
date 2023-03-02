package com.teamzzong.hacker.data.model.response.battle

import com.teamzzong.hacker.domain.entity.battle.Member
import com.teamzzong.hacker.domain.entity.battle.Team
import com.teamzzong.hacker.domain.entity.battle.TeamLog
import com.teamzzong.hacker.domain.entity.battle.TeamStatus
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTeam(
    val isMyTeam: Boolean,
    val team: ResponseTeamStatus,
    val members: List<ResponseMember>,
    val logs: List<ResponseTeamLog>
) {
    fun toTeamStatus(): TeamStatus {
        return TeamStatus(team.name, team.imageUrl, team.commitCount, team.hairCount, team.couponCount, team.head, team.face)
    }

    fun toMember(): List<Member> {
        return members.map {
            Member(it.userId, it.nickname, it.head, it.face)
        }
    }

    fun toTeamLog(): List<TeamLog> {
        return logs.map {
            TeamLog(it.date, it.content)
        }
    }
    fun toTeam(): Team {
        return Team(isMyTeam,team.toTeamStatus(),members.map { it.toMember() },logs.map { it.toTeamLog() })
    }
}

@Serializable
data class ResponseTeamStatus(
    val name: String,
    val imageUrl: String,
    val commitCount: Int,
    val hairCount: Int,
    val couponCount: Int,
    val head: String?,
    val face: String
) {
    fun toTeamStatus(): TeamStatus {
        return TeamStatus(name, imageUrl,commitCount, hairCount, couponCount, head, face)
    }
}

@Serializable
data class ResponseMember(
    val userId: Int,
    val nickname: String,
    val head: String?,
    val face: String
) {
    fun toMember(): Member {
        return Member(userId, nickname, head, face)
    }
}

@Serializable
data class ResponseTeamLog(
    val date: String,
    val content: MutableList<String>
) {
    fun toTeamLog(): TeamLog {
        return TeamLog(date, content)
    }
}