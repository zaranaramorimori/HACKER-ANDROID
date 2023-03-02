package com.teamzzong.hacker.domain.repository.auth

import com.teamzzong.hacker.domain.entity.auth.AuthState
import com.teamzzong.hacker.domain.entity.auth.PhotoUrl
import com.teamzzong.hacker.domain.entity.auth.SignUp

interface AuthRepository {
    suspend fun postGithubId(githubId: String): PhotoUrl
    suspend fun inHouseLogin(token: String, social: String = "kakao"): Result<AuthState>
    suspend fun postSignUp(auth: SignUp)
    fun setAccessToken(token: String)
    fun setRefreshToken(token: String)
    fun setUserUUID(uuid: String)
    fun setSocial(social: String)
}
