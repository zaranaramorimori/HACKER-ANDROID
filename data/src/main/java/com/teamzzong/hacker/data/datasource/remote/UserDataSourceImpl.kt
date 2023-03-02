package com.teamzzong.hacker.data.datasource.remote

import com.teamzzong.hacker.data.datasource.UserDataSource
import com.teamzzong.hacker.data.model.request.RequestNickName
import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.user.ResponseChange
import com.teamzzong.hacker.data.remote.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val service: UserService
) : UserDataSource {
    override suspend fun changeNickName(requestNickName: RequestNickName): BaseResponse<ResponseChange> {
        return service.changeNickName(requestNickName)
    }
}