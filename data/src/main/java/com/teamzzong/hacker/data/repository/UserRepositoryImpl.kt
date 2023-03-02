package com.teamzzong.hacker.data.repository

import com.teamzzong.hacker.data.datasource.UserDataSource
import com.teamzzong.hacker.data.model.request.toRequest
import com.teamzzong.hacker.data.remote.UserService
import com.teamzzong.hacker.domain.entity.user.Change
import com.teamzzong.hacker.domain.entity.user.NickName
import com.teamzzong.hacker.domain.entity.user.DetailProfile
import com.teamzzong.hacker.domain.entity.user.Profile
import com.teamzzong.hacker.domain.entity.user.TotalRank
import com.teamzzong.hacker.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: UserService,
    private val datasource: UserDataSource
) : UserRepository {
    override suspend fun getMyProfile(): Profile {
        return service.fetchMyProfile().data.toEntity()
    }

    override suspend fun changeNickName(nickName: NickName): Change {
        return datasource.changeNickName(nickName.toRequest()).data.toChange()
    }

    override suspend fun getTotalRank(): TotalRank {
        return service.getTotalRank().data.toEntity()
    }

    override suspend fun getMyDetailProfile(): DetailProfile {
        return service.fetchMyDetailProfile().data.toEntity()
    }

    override suspend fun exchangeCoupon() {
        service.exchangeCoupon()
    }
}
