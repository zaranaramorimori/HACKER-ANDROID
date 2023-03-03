package com.teamzzong.hacker.data.repository

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor
import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor.KakaoLoginState.Token
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KakaoAuthInteractorImpl @Inject constructor(
    private val kakaoClient: UserApiClient,
    @ActivityContext private val context: Context
) : KakaoAuthInteractor {
    override val isKakaoTalkLoginAvailable: Boolean
        get() = kakaoClient.isKakaoTalkLoginAvailable(context)

    override suspend fun loginByKakaoTalk(): Result<Token> = suspendCancellableCoroutine {
        kakaoClient.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                it.resume(Result.failure(error))
                return@loginWithKakaoTalk
            }
            if (token != null) {
                it.resume(Result.success(Token(token.accessToken)))
                return@loginWithKakaoTalk
            }
            it.resumeWithException(Throwable("Unreachable code in KakaoLogin"))
        }
    }

    override suspend fun loginByKakaoAccount(): Result<Token> = suspendCancellableCoroutine {
        kakaoClient.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                it.resume(Result.failure(error))
                return@loginWithKakaoAccount
            }
            if (token != null) {
                it.resume(Result.success(Token(token.accessToken)))
                return@loginWithKakaoAccount
            }
            it.resumeWithException(Throwable("Unreachable code in KakaoLogin"))
        }
    }

    override fun logout() {
        kakaoClient.logout(Timber::e)
    }

    override fun expire() {
        kakaoClient.unlink(Timber::e)
    }
}