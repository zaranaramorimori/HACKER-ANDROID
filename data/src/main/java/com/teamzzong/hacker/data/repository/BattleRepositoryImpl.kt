package com.teamzzong.hacker.data.repository

import com.teamzzong.hacker.data.datasource.BattleDataSource
import com.teamzzong.hacker.data.model.response.battle.toSeason
import com.teamzzong.hacker.domain.entity.battle.*
import com.teamzzong.hacker.domain.repository.battle.BattleRepository
import javax.inject.Inject

class BattleRepositoryImpl @Inject constructor(
    private val dataSource: BattleDataSource
) : BattleRepository {
    override suspend fun getBattleSeason(): List<Season> {
        return dataSource.getBattleSeason().data.toSeason()
    }

    override suspend fun getBattleRank(seasonId: Int): Rank {
        return dataSource.getBattleRank(seasonId).data.toTeamRank()
    }

    override suspend fun getAttackTeam(teamId: Int): Team {
        return dataSource.getBattleTeam(teamId).data.toTeam()
    }

    override suspend fun postAttackTeam(teamId: Int): Base {
        return dataSource.postAttackTeam(teamId).toBase()
    }

}