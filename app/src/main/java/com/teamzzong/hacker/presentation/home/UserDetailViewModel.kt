package com.teamzzong.hacker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.user.DetailProfile
import com.teamzzong.hacker.domain.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _userDetailState = MutableStateFlow<DetailProfile?>(null)
    val userDetailState = _userDetailState.asStateFlow()

    init {
        setUserDetailScreen()
    }

    private fun setUserDetailScreen() {
        viewModelScope.launch {
            runCatching { repository.getMyDetailProfile() }
                .onSuccess { _userDetailState.value = it }
                .onFailure { Timber.e(it) }
        }
    }
}