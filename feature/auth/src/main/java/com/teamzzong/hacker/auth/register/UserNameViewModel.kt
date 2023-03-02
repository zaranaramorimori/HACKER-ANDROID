package com.teamzzong.hacker.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.auth.PhotoUrl
import com.teamzzong.hacker.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserNameViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val name = MutableStateFlow("")
    val isClickable: Flow<Boolean> = name.map { it.isNotBlank() }

    private val _checkId = MutableSharedFlow<Event>()
    val checkId = _checkId.asSharedFlow()

    fun postGithubId(id: String) {
        viewModelScope.launch {
            runCatching {
                repository.postGithubId(id)
            }.onSuccess {
                _checkId.emit(Event.Success(it))
            }.onFailure {
                _checkId.emit(Event.Failure("존재하지 않는 아이디 입니다."))
            }
        }
    }

    sealed class Event {
        data class Success(val url: PhotoUrl) : Event()
        data class Failure(val msg: String) : Event()
    }
}