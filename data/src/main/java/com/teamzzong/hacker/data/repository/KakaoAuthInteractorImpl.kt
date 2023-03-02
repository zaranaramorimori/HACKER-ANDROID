package com.teamzzong.hacker.data.repository

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor
import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor.*
import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor.KakaoLoginState.*
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

class KakaoAuthInteractorImpl @Inject constructor(
    private val kakaoClient: UserApiClient,
    @ActivityContext private val context: Context
) : KakaoAuthInteractor {
    override val isKakaoTalkLoginAvailable: Boolean
        get() = kakaoClient.isKakaoTalkLoginAvailable(context)
    private val _loginState: MutableStateFlow<KakaoLoginState> = MutableStateFlow(Init)
    override val loginState: StateFlow<KakaoLoginState> = _loginState.asStateFlow()
    private val kakaoAuthCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        error?.let(::handleLoginError)
        token?.let(::handleLoginSuccess)
    }

    override fun loginByKakaoTalk() {
        kakaoClient.loginWithKakaoTalk(context, callback = kakaoAuthCallback)
    }

    override fun loginByKakaoAccount() {
        kakaoClient.loginWithKakaoAccount(context, callback = kakaoAuthCallback)
    }

    private fun handleLoginError(throwable: Throwable) {
        _loginState.value = Failure(throwable)
    }

    private fun handleLoginSuccess(oAuthToken: OAuthToken) {
        kakaoClient.me { user, _ ->
            _loginState.value = Success(oAuthToken.accessToken, user?.id.toString())
        }
    }

    override fun logout() {
        kakaoClient.logout(Timber::e)
    }

    override fun expire() {
        kakaoClient.unlink(Timber::e)
    }
}