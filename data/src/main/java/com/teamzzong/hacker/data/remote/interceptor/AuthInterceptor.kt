package com.teamzzong.hacker.data.remote.interceptor

import android.content.Context
import com.jakewharton.processphoenix.ProcessPhoenix
import com.teamzzong.hacker.data.model.response.auth.ResponseAuthToken
import com.teamzzong.hacker.domain.HackerDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

private const val BASE_URL = "http://3.36.251.74:5000/"

class AuthInterceptor @Inject constructor(
    private val dataStore: HackerDataStore,
    private val json: Json,
    @ApplicationContext private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = if (isLogin(originalRequest)) originalRequest else
            originalRequest.newBuilder().addHeader("Authorization", dataStore.userToken).build()
        val response = chain.proceed(authRequest)
        when (response.code()) {
            401 -> {
                try {
                    val refreshTokenRequest = originalRequest.newBuilder().get()
                        .url("${BASE_URL}auth/token")
                        .addHeader("accesstoken", dataStore.userToken)
                        .addHeader("refreshtoken", dataStore.refreshToken)
                        .build()
                    val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                    if (refreshTokenResponse.isSuccessful) {
                        val responseToken: ResponseAuthToken =
                            json.decodeFromString(
                                refreshTokenResponse.body()?.string()
                                    ?: throw IllegalStateException("refreshTokenResponse is null $refreshTokenResponse")
                            )
                        with(dataStore) {
                            userToken = responseToken.accessToken
                            refreshToken = responseToken.refreshToken
                        }
                        refreshTokenResponse.close()
                        val newRequest = originalRequest.newBuilder()
                            .addHeader("Authorization", dataStore.userToken)
                            .build()
                        return chain.proceed(newRequest)
                    } else {
                        dataStore.userToken = ""
                        dataStore.refreshToken = ""
                    }
                } catch (e: Throwable) {
                    Timber.e(e)
                    dataStore.userToken = ""
                    dataStore.refreshToken = ""
                    ProcessPhoenix.triggerRebirth(context)
                }
            }
        }
        return response
    }

    private fun isLogin(originalRequest: Request) =
        when {
            originalRequest.url().encodedPath().contains("devicetoken") -> false
            originalRequest.url().encodedPath().contains("token") -> true
            originalRequest.url().encodedPath().contains("auth") -> true
            else -> false
        }
}