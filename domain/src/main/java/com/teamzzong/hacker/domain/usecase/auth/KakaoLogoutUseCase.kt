package com.teamzzong.hacker.domain.usecase.auth

import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor
import javax.inject.Inject

class KakaoLogoutUseCase @Inject constructor(
    private val kakaoAuthInteractor: KakaoAuthInteractor
) {
    operator fun invoke() {
        kakaoAuthInteractor.logout()
    }
}
