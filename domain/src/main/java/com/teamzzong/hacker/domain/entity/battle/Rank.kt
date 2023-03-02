package com.teamzzong.hacker.domain.entity.battle

data class Rank(
    val myTeam: MyTeam,
    val Teams: List<Teams>
)

data class MyTeam(
    val teamId: Int,
    val rank: Int,
    val name: String,
    val commitCount: Int
)

data class Teams(
    val teamId: Int,
    val rank: Int,
    val name: String,
    val commitCount: Int,
    val hairCount: Int,
    val head: String?,
    val face: String
)