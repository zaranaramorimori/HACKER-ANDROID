package com.teamzzong.hacker.domain.repository.social

interface KakaoAuthInteractor {
    val isKakaoTalkLoginAvailable: Boolean
    suspend fun loginByKakaoTalk(): Result<Token>
    suspend fun loginByKakaoAccount(): Result<Token>
    fun logout()
    fun expire()
    data class Token(val token: String)

}
