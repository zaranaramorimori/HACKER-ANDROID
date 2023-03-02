package com.teamzzong.hacker.data.remote

import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.battle.ResponseEmpty
import com.teamzzong.hacker.data.model.response.battle.ResponseRank
import com.teamzzong.hacker.data.model.response.battle.ResponseSeason
import com.teamzzong.hacker.data.model.response.battle.ResponseTeam
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BattleService {
    @GET("battle")
    suspend fun getBattleSeason(): BaseResponse<ResponseSeason>

    @GET("battle/season/{seasonId}")
    suspend fun getBattleRank(
        @Path("seasonId") seasonId: Int
    ): BaseResponse<ResponseRank>

    @GET("battle/team/{teamId}")
    suspend fun getBattleTeam(
        @Path("teamId") teamId: Int
    ): BaseResponse<ResponseTeam>

    @POST("attack/team/{teamId}")
    suspend fun postAttackTam(
        @Path("teamId") teamId: Int
    ): ResponseEmpty
}