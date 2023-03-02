package com.teamzzong.hacker.data.datasource

import com.teamzzong.hacker.data.model.request.RequestSignUp
import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.auth.ResponseAuthToken
import com.teamzzong.hacker.data.model.response.auth.ResponsePhotoUrl

interface AuthDataSource {
    suspend fun postGithubId(githubId: String): BaseResponse<ResponsePhotoUrl>
    suspend fun postSignUp(auth: RequestSignUp): BaseResponse<ResponseAuthToken>
}