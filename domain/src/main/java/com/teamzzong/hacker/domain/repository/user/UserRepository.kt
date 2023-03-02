package com.teamzzong.hacker.domain.repository.user

import com.teamzzong.hacker.domain.entity.user.Change
import com.teamzzong.hacker.domain.entity.user.NickName
import com.teamzzong.hacker.domain.entity.user.DetailProfile
import com.teamzzong.hacker.domain.entity.user.Profile
import com.teamzzong.hacker.domain.entity.user.TotalRank

interface UserRepository {
    suspend fun getMyProfile(): Profile
    suspend fun changeNickName(nickName: NickName): Change
    suspend fun getTotalRank(): TotalRank
    suspend fun getMyDetailProfile(): DetailProfile
    suspend fun exchangeCoupon()
}
