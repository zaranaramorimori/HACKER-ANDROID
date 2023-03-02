package com.teamzzong.hacker.data.datasource

import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.battle.ResponseEmpty
import com.teamzzong.hacker.data.model.response.battle.ResponseRank
import com.teamzzong.hacker.data.model.response.battle.ResponseSeason
import com.teamzzong.hacker.data.model.response.battle.ResponseTeam

interface BattleDataSource {
    suspend fun getBattleSeason(): BaseResponse<ResponseSeason>
    suspend fun getBattleRank(seasonId: Int): BaseResponse<ResponseRank>
    suspend fun getBattleTeam(teamId: Int) : BaseResponse<ResponseTeam>
    suspend fun postAttackTeam(teamId: Int) : ResponseEmpty
}