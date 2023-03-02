package com.teamzzong.hacker.data.repository

import com.teamzzong.hacker.data.datasource.AuthDataSource
import com.teamzzong.hacker.data.model.request.toRequest
import com.teamzzong.hacker.data.model.response.auth.ResponseLogin
import com.teamzzong.hacker.data.model.response.auth.ResponseRegisterUser
import com.teamzzong.hacker.data.remote.AuthService
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.domain.entity.auth.AuthState
import com.teamzzong.hacker.domain.entity.auth.PhotoUrl
import com.teamzzong.hacker.domain.entity.auth.SignUp
import com.teamzzong.hacker.domain.repository.auth.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource,
    private val service: AuthService,
    private val dataStore: HackerDataStore
) : AuthRepository {
    override suspend fun postGithubId(githubId: String): PhotoUrl {
        return dataSource.postGithubId(githubId).data.toPhotoUrl()
    }

    override suspend fun inHouseLogin(token: String, social: String): Result<AuthState> {
        runCatching { service.executeInHouseLogin(token, social) }
            .onSuccess {
                return when (it.data) {
                    is ResponseLogin -> {
                        setAccessToken(it.data.accessToken)
                        setRefreshToken(it.data.refreshToken)
                        dataStore.userId = it.data.id
                        dataStore.githubNickName = it.data.userName
                        dataStore.hackerNickName = it.data.nickName
                        Result.success(AuthState.SIGN_IN)
                    }
                    is ResponseRegisterUser -> {
                        setUserUUID(it.data.uuid)
                        setSocial(it.data.social)
                        Result.success(AuthState.SIGN_UP)
                    }
                }
            }.onFailure { return Result.failure(it) }
        return Result.failure(Throwable("Unreachable Excpetion: AuthRepositoryImpl\$socialLogin"))
    }

    override suspend fun postSignUp(auth: SignUp) {
        runCatching {
            dataSource.postSignUp(auth.toRequest()).data.toAuthToken()
        }.onSuccess {
            dataStore.refreshToken = it.refreshToken
            dataStore.userToken = it.accessToken
        }.onFailure(Timber::e)
    }

    override fun setAccessToken(token: String) {
        dataStore.userToken = token
    }

    override fun setRefreshToken(token: String) {
        dataStore.refreshToken = token
    }

    override fun setUserUUID(uuid: String) {
        dataStore.userUUID = uuid
    }

    override fun setSocial(social: String) {
        dataStore.social = social
    }
}