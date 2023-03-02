package com.teamzzong.hacker.domain.usecase.auth

import com.teamzzong.hacker.domain.HackerDataStore
import javax.inject.Inject

class IsAutoLoginEnabledUseCase @Inject constructor(
    private val dataStore: HackerDataStore
) {
    operator fun invoke() = dataStore.userToken != "" && dataStore.userId != -1
}
