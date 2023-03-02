package com.teamzzong.hacker.presentation.battle.season

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.battle.Season
import com.teamzzong.hacker.domain.repository.battle.BattleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BattleSeasonViewModel @Inject constructor(
    private val repository: BattleRepository
) : ViewModel() {
    private val _seasonList = MutableStateFlow<List<Season>>(listOf())
    val seasonList: StateFlow<List<Season>> = _seasonList.asStateFlow()

    fun getSeason() {
        viewModelScope.launch {
            runCatching {
                repository.getBattleSeason()
            }.onSuccess {
                _seasonList.value = it
            }.onFailure(Timber::e)
        }
    }
}