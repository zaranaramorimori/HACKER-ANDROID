package com.teamzzong.hacker.data.datasource.remote

import com.teamzzong.hacker.data.datasource.AuthDataSource
import com.teamzzong.hacker.data.model.request.RequestSignUp
import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.auth.ResponseAuthToken
import com.teamzzong.hacker.data.model.response.auth.ResponsePhotoUrl
import com.teamzzong.hacker.data.remote.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthService
) : AuthDataSource {
    override suspend fun postGithubId(githubId: String): BaseResponse<ResponsePhotoUrl> {
        return service.postGithubId(githubId)
    }

    override suspend fun postSignUp(auth: RequestSignUp): BaseResponse<ResponseAuthToken> {
        return service.postSignUp(auth)
    }
}