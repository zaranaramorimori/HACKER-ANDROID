package com.teamzzong.hacker.domain.entity.battle

data class Team(
    val isMyTeam: Boolean,
    val team: TeamStatus,
    val members: List<Member>,
    val logs: List<TeamLog>
)

data class TeamStatus(
    val name: String,
    val imageUrl: String,
    val commitCount: Int,
    val hairCount: Int,
    val couponCount: Int,
    val head: String?,
    val face: String
)

data class Member(
    val userId: Int,
    val nickname: String,
    val head: String?,
    val face: String
)

data class TeamLog(
    val date: String,
    val content: MutableList<String>
)