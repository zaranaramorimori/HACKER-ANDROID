package com.teamzzong.hacker.data.remote

import com.teamzzong.hacker.data.model.response.BaseResponse
import com.teamzzong.hacker.data.model.response.push.ResponseNotification
import retrofit2.http.GET

interface NotificationService {
    @GET("push")
    suspend fun fetchNotifications(): BaseResponse<ResponseNotification>
}
