package com.teamzzong.hacker.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.auth.SignUp
import com.teamzzong.hacker.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NickNameViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val nickName = MutableStateFlow("")
    val isClickable: Flow<Boolean> = nickName.map { it.isNotBlank() }

    private val _checkNickName = MutableSharedFlow<Event>()
    val checkNickName = _checkNickName.asSharedFlow()

    fun postSignUp(auth: SignUp) {
        viewModelScope.launch {
            runCatching {
                repository.postSignUp(auth)
            }.onSuccess {
                _checkNickName.emit(Event.Success)
            }.onFailure {
                _checkNickName.emit(Event.Failure("앗! 이미 사용중인 이름이에요"))
            }
        }
    }

    sealed class Event {
        object Success : Event()
        data class Failure(val msg: String) : Event()
        data class TrueNickName(val msg: String) : Event()
        data class FalseNickName(val msg: String) : Event()
    }
}
