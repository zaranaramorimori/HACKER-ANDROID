package com.teamzzong.hacker.data.model.response.user

import com.teamzzong.hacker.domain.entity.user.OtherRankInfo
import com.teamzzong.hacker.domain.entity.user.TotalRank
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias RankEntity = com.teamzzong.hacker.domain.entity.user.Rank

@Serializable
data class ResponseTotalRank(
    @SerialName("myRank")
    val myRank: MyRank?,
    @SerialName("ranks")
    val ranks: List<Rank>
) {
    @Serializable
    data class MyRank(
        @SerialName("commitCount")
        val commitCount: Int,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("rank")
        val rank: Int,
        @SerialName("userId")
        val userId: Int
    ) {
        fun toEntity() = RankEntity(
            userId = userId,
            rank = rank,
            nickname = nickname,
            commitCount = commitCount
        )
    }

    @Serializable
    data class Rank(
        @SerialName("commitCount")
        val commitCount: Int,
        @SerialName("head")
        val head: String?,
        @SerialName("face")
        val face: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("rank")
        val rank: Int,
        @SerialName("userId")
        val userId: Int
    ) {
        fun toEntity() = OtherRankInfo(
            rank = RankEntity(userId, rank, nickname, commitCount),
            head = head,
            face = face
        )
    }

    fun toEntity() = TotalRank(
        myRank = myRank?.toEntity(),
        otherRankInfo = ranks.map { it.toEntity() }
    )
}
