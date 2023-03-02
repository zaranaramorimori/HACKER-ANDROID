package com.teamzzong.hacker.presentation.gallery.content.gallery.search

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.R
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.domain.entity.user.GithubUser
import com.teamzzong.hacker.domain.repository.friend.FriendRepository
import com.teamzzong.hacker.domain.usecase.friend.SearchFriendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchFriendViewModel @Inject constructor(
    private val searchFriendUseCase: SearchFriendUseCase,
    private val friendRepository: FriendRepository,
    private val hackerDataStore: HackerDataStore
) : ViewModel() {
    // 양방향 데이터 바인딩
    val name = MutableStateFlow("")

    private val isFocused = MutableStateFlow(false)
    val isFriendAddAvailable = name.combine(isFocused) { _, isFocused ->
        when {
            !isFocused -> Status.INIT
            else -> Status.AVAILABLE
        }
    }
    val errorStatus = MutableSharedFlow<SearchErrorStatus>()
    private val _friendList = MutableStateFlow<List<GithubUser>?>(null)
    val friendList = _friendList.asStateFlow()
    private val selectedUser = MutableStateFlow<GithubUser?>(null)
    val isFriendSelectComplete = selectedUser.map { it != null }
    val isFriendAddSuccess = MutableSharedFlow<Boolean>()

    fun setFocusState(state: Boolean) {
        isFocused.value = state
    }

    fun setFriend(item: GithubUser) {
        selectedUser.value = item
    }

    fun searchUser() {
        viewModelScope.launch {
            searchFriendUseCase(name.value)
                .onSuccess {
                    _friendList.value = it.filter { it.id != hackerDataStore.userId }
                }.onFailure {
                    errorStatus.emit(SearchErrorStatus.messageOf(it.message ?: ""))
                }
        }
    }

    fun addFriend() {
        viewModelScope.launch {
            runCatching {
                friendRepository.changeFriendStatus(
                    selectedUser.value?.id
                        ?: throw IllegalStateException("선택한 친구의 ID가 존재하지 않습니다 user: ${selectedUser.value}")
                )
            }.fold({
                isFriendAddSuccess.emit(true)
            }, {
                Timber.e(it)
                isFriendAddSuccess.emit(false)
            })
        }
    }

    enum class Status(
        val title: String,
        val content: String,
        @DrawableRes val resId: Int
    ) {
        INIT("친구를 찾아봐요!", "추가할 Github 유저 네임을 적어주세요", R.drawable.ic_x_white),
        AVAILABLE("친구를 찾아봐요!", "추가할 Github 유저 네임을 적어주세요", R.drawable.ic_x_black),
        IS_ME("앗!", "자신은 친구로 등록할 수 없어요!", R.drawable.ic_error);
    }

    enum class SearchErrorStatus(
        val errorMessage: String
    ) {
        NOT_IN_GITHUB("존재하지 않는 깃허브입니다."),
        NOT_IN_HACKER("탈퇴했거나 가입하지 않은 유저입니다.");

        companion object {
            fun messageOf(message: String) = values().find { it.errorMessage == message }
                ?: throw IllegalStateException("해당 ErrorMessage는 존재하지 않습니다 message: $message")
        }
    }
}
