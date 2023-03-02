package com.teamzzong.hacker.presentation.gallery.content.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.friend.Friend
import com.teamzzong.hacker.domain.repository.friend.FriendRepository
import com.teamzzong.hacker.presentation.gallery.model.GalleryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: FriendRepository
) : ViewModel() {
    private val _friends: MutableStateFlow<List<Friend>?> = MutableStateFlow(null)
    val friends = _friends.filterNotNull()
        .mapLatest {
            val friendList = it.map { it.toUiState() }
            listOf<GalleryUiState>(GalleryUiState.Add) + friendList
        }

    init {
        viewModelScope.launch {
            repository.getFriends()
                .onSuccess { _friends.value = it }
                .onFailure(Timber::equals)
        }
    }

    fun refreshList() {
        viewModelScope.launch {
            repository.getFriends()
                .onSuccess { _friends.value = it }
                .onFailure(Timber::equals)
        }
    }

    private fun Friend.toUiState() = GalleryUiState.User(
        id = userId,
        nickName = nickName,
        head = headImageUrl,
        face = faceImageUrl
    )
}
