package com.teamzzong.hacker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.user.Profile
import com.teamzzong.hacker.domain.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _userState = MutableSharedFlow<Profile?>()
    val userState = _userState.asSharedFlow()

    init {
        setHomeScreen()
    }

    fun setHomeScreen() {
        viewModelScope.launch {
            runCatching { repository.getMyProfile() }
                .onSuccess { _userState.emit(it) }
                .onFailure { Timber.e(it) }
        }
    }

    fun exchangeCoupon() {
        viewModelScope.launch {
            runCatching { repository.exchangeCoupon() }
                .onSuccess { setHomeScreen() }
                .onFailure { Timber.e(it) }
        }
    }
}
