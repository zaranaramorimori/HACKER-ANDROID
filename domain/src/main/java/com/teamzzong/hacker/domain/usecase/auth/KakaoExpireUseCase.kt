package com.teamzzong.hacker.domain.usecase.auth

import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor
import javax.inject.Inject

class KakaoExpireUseCase @Inject constructor(
    private val kakaoAuthInteractor: KakaoAuthInteractor
) {
    operator fun invoke() {
        kakaoAuthInteractor.expire()
    }
}
