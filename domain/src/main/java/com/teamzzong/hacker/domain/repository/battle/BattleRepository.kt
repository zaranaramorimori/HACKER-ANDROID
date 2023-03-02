package com.teamzzong.hacker.domain.repository.battle

import com.teamzzong.hacker.domain.entity.battle.*

interface BattleRepository {
    suspend fun getBattleSeason(): List<Season>
    suspend fun getBattleRank(seasonId: Int): Rank
    suspend fun getAttackTeam(teamId: Int): Team
    suspend fun postAttackTeam(teamId: Int): Base
}