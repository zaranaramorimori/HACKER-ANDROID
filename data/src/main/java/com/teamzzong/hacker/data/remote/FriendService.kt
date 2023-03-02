package com.teamzzong.hacker.data.remote

import com.teamzzong.hacker.data.model.request.RequestChangeFriendStatus
import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.EmptyResponse
import com.teamzzong.hacker.data.model.response.friend.ResponseFriend
import com.teamzzong.hacker.data.model.response.friend.ResponseFriendStatus
import com.teamzzong.hacker.data.model.response.friend.ResponseSearchFriend
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendService {
    @GET("friend")
    suspend fun getFriends(): BaseResponse<List<ResponseFriend>>

    @POST("friend")
    suspend fun changeFriendStatus(
        @Body body: RequestChangeFriendStatus
    ): BaseResponse<ResponseFriendStatus>

    @POST("attack/user/{userId}")
    suspend fun attackUser(@Path("userId") userId: Int): EmptyResponse

    @GET("friend/github/{userName}")
    suspend fun getUsersAsFriend(
        @Path("userName") userName: String
    ): Response<BaseResponse<List<ResponseSearchFriend>>>
}
