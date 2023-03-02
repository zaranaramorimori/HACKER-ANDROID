package com.teamzzong.hacker.data.remote

import androidx.annotation.Keep
import com.teamzzong.hacker.data.model.request.RequestDeviceToken
import com.teamzzong.hacker.data.model.request.RequestNickName
import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.EmptyResponse
import com.teamzzong.hacker.data.model.response.user.*
import retrofit2.http.*

interface UserService {
    @GET("user")
    suspend fun fetchMyProfile(): BaseResponse<ResponseMyProfile>

    @DELETE("user")
    suspend fun expireUser(): EmptyResponse

    @PUT("user/nickname")
    suspend fun changeNickName(
        @Body body: RequestNickName
    ): BaseResponse<ResponseChange>

    @GET("rank")
    suspend fun getTotalRank(): BaseResponse<ResponseTotalRank>

    @GET("user/detail")
    suspend fun fetchMyDetailProfile(): BaseResponse<ResponseDetailProfile>

    @POST("attack/coupon")
    suspend fun exchangeCoupon(): EmptyResponse

    @GET("user/detail/{userId}")
    suspend fun getUserDetail(
        @Path("userId") userId: Int
    ): BaseResponse<ResponseUserDetail>

    @Keep
    @POST("user/devicetoken")
    suspend fun registerFirebaseToken(
        @Header("token") token: String,
        @Body body: RequestDeviceToken
    ): BaseResponse<ResponseDeviceToken>
}
