package com.teamzzong.hacker.data.remote

import com.teamzzong.hacker.data.model.request.RequestSignUp
import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.auth.ResponseAuthToken
import com.teamzzong.hacker.data.model.response.auth.ResponseInHouseAuth
import com.teamzzong.hacker.data.model.response.auth.ResponsePhotoUrl
import retrofit2.http.*

interface AuthService {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("auth/{social}")
    suspend fun executeInHouseLogin(
        @Header("token") token: String,
        @Path("social") social: String
    ): BaseResponse<ResponseInHouseAuth>

    @GET("auth/github/{username}")
    suspend fun postGithubId(
        @Path("username") userName: String
    ): BaseResponse<ResponsePhotoUrl>

    @POST("auth")
    suspend fun postSignUp(
        @Body body: RequestSignUp
    ): BaseResponse<ResponseAuthToken>
}