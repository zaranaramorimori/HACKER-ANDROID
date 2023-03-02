package com.teamzzong.hacker.data.datasource.remote

import com.teamzzong.hacker.data.datasource.BattleDataSource
import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.battle.ResponseEmpty
import com.teamzzong.hacker.data.model.response.battle.ResponseRank
import com.teamzzong.hacker.data.model.response.battle.ResponseSeason
import com.teamzzong.hacker.data.model.response.battle.ResponseTeam
import com.teamzzong.hacker.data.remote.BattleService
import javax.inject.Inject

class BattleDataSourceImpl @Inject constructor(
    private val service: BattleService
) : BattleDataSource {
    override suspend fun getBattleSeason(): BaseResponse<ResponseSeason> {
        return service.getBattleSeason()
    }

    override suspend fun getBattleRank(seasonId: Int): BaseResponse<ResponseRank> {
        return service.getBattleRank(seasonId)
    }

    override suspend fun getBattleTeam(teamId: Int): BaseResponse<ResponseTeam> {
        return service.getBattleTeam(teamId)
    }

    override suspend fun postAttackTeam(teamId: Int): ResponseEmpty {
        return service.postAttackTam(teamId)
    }
}