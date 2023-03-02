package com.teamzzong.hacker.domain.usecase.auth

import com.teamzzong.hacker.domain.repository.auth.AuthRepository
import javax.inject.Inject

class InHouseLoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(token: String) = repository.inHouseLogin(token)
}
