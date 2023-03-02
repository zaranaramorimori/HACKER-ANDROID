package com.teamzzong.hacker.domain.repository.social

import kotlinx.coroutines.flow.StateFlow

interface KakaoAuthInteractor {
    val isKakaoTalkLoginAvailable: Boolean
    val loginState: StateFlow<KakaoLoginState>
    fun loginByKakaoTalk()
    fun loginByKakaoAccount()
    fun logout()
    fun expire()

    sealed class KakaoLoginState {
        object Init : KakaoLoginState()
        data class Success(val token: String, val id: String) : KakaoLoginState()
        data class Failure(val error: Throwable) : KakaoLoginState()
    }
}
