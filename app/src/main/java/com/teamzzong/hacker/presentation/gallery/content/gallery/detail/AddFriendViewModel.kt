package com.teamzzong.hacker.presentation.gallery.content.gallery.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.user.UserDetail
import com.teamzzong.hacker.domain.repository.friend.FriendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel @Inject constructor(
    private val repository: FriendRepository
) : ViewModel() {
    private val _userDetail: MutableStateFlow<UserDetail?> = MutableStateFlow(null)
    val isAttackEnabled = _userDetail.filterNotNull()
        .map { it.couponCount > 0 }
    val isMyFriend = _userDetail.filterNotNull()
        .map { it.isMyFriend }
    val userDetail = _userDetail.filterNotNull()
    val toastMessage = MutableSharedFlow<String>()

    fun setUserDetailScreen(userId: Int) {
        viewModelScope.launch {
            repository.getUserDetail(userId)
                .onSuccess { _userDetail.value = it }
                .onFailure(Timber::e)
        }
    }

    fun changeFriendStatusOf(userId: Int) {
        viewModelScope.launch {
            repository.changeFriendStatus(userId)
                .onSuccess { setUserDetailScreen(userId) }
                .onFailure(Timber::e)
        }
    }

    fun attackUserOf(userId: Int) {
        viewModelScope.launch {
            repository.attackUserOf(userId)
                .onSuccess { setUserDetailScreen(userId) }
                .onFailure {
                    Timber.e(it)
                    toastMessage.tryEmit("연결 상황이 좋지 않아 공격에 실패했습니다. 다시 시도해주세요.")
                }
        }
    }
}
