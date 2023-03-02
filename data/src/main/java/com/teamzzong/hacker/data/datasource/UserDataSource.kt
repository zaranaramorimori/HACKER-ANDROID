package com.teamzzong.hacker.data.datasource

import com.teamzzong.hacker.data.model.request.RequestNickName
import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.user.ResponseChange

interface UserDataSource {
    suspend fun changeNickName(requestNickName: RequestNickName): BaseResponse<ResponseChange>
}