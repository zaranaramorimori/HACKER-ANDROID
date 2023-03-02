package com.teamzzong.hacker.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.domain.entity.user.NickName
import com.teamzzong.hacker.domain.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeNameViewModel @Inject constructor(
    private val repository: UserRepository,
    // Hotfix로 인해 dataStore 여기에 추가합니다!
    private val dataStore: HackerDataStore
) : ViewModel() {
    val nickName = MutableStateFlow("")
    val isClickable: Flow<Boolean> = nickName.map { it.isNotBlank() }

    private val _changeResult = MutableSharedFlow<Event>()
    val changeResult = _changeResult.asSharedFlow()

    fun changeNickName(nickName: NickName) {
        viewModelScope.launch {
            runCatching {
                repository.changeNickName(nickName)
            }.onSuccess {
                dataStore.hackerNickName = nickName.nickName
                _changeResult.emit(Event.SuccessChange("닉네임 변경 완료"))
            }.onFailure {
                _changeResult.emit(Event.FailChange("앗! 이미 사용중인 이름이에요"))
            }
        }
    }

    sealed class Event {
        data class SuccessChange(val msg: String) : Event()
        data class FailChange(val msg: String) : Event()
    }
}