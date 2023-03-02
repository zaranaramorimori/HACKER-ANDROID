package com.teamzzong.hacker.auth.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.auth.AuthState
import com.teamzzong.hacker.domain.usecase.auth.InHouseLoginUseCase
import com.teamzzong.hacker.domain.usecase.auth.IsAutoLoginEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val inHouseLoginUseCase: InHouseLoginUseCase,
    private val isAutoLoginEnabledUseCase: IsAutoLoginEnabledUseCase
) : ViewModel() {
    val splashAnimationStarted = MutableStateFlow(false)

    private val _inHouseLoginLoginUiState: MutableState<InHouseLoginUiState> =
        mutableStateOf(InHouseLoginUiState.InHouseLoginInit)
    val inHouseLoginLoginUiState: State<InHouseLoginUiState> = _inHouseLoginLoginUiState
    val isAutoLoginEnabled: Boolean
        get() = isAutoLoginEnabledUseCase()

    fun changeSplashStartState(state: Boolean) {
        splashAnimationStarted.value = state
    }

    fun executeInHouseLogin(token: String) {
        viewModelScope.launch {
            inHouseLoginUseCase(token)
                .onSuccess {
                    _inHouseLoginLoginUiState.value = when (it) {
                        AuthState.SIGN_IN -> InHouseLoginUiState.SignInSuccess
                        AuthState.SIGN_UP -> InHouseLoginUiState.SignUpSuccess
                    }
                }.onFailure {
                    Timber.e(it)
                    _inHouseLoginLoginUiState.value =
                        InHouseLoginUiState.Failure(it.message ?: "에러가 발생했습니다")
                }
        }
    }
}

sealed class InHouseLoginUiState {
    object InHouseLoginInit : InHouseLoginUiState()
    object SignInSuccess : InHouseLoginUiState()
    object SignUpSuccess : InHouseLoginUiState()
    data class Failure(val message: String) : InHouseLoginUiState()
}
