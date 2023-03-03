package com.teamzzong.hacker.domain.repository.social

interface KakaoAuthInteractor {
    val isKakaoTalkLoginAvailable: Boolean
    suspend fun loginByKakaoTalk(): Result<KakaoLoginState.Token>
    suspend fun loginByKakaoAccount(): Result<KakaoLoginState.Token>
    fun logout()
    fun expire()

    sealed class KakaoLoginState {
        data class Token(val token: String) : KakaoLoginState()
    }
}
