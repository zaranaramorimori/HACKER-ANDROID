package com.teamzzong.hacker.domain.entity.user

data class TotalRank(
    val myRank: Rank?,
    val otherRankInfo: List<OtherRankInfo>
)
