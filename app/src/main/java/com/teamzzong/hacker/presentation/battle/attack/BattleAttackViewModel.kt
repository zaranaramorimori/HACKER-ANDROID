package com.teamzzong.hacker.presentation.battle.attack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.battle.Member
import com.teamzzong.hacker.domain.entity.battle.Team
import com.teamzzong.hacker.domain.entity.battle.TeamLog
import com.teamzzong.hacker.domain.repository.battle.BattleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class BattleAttackViewModel @Inject constructor(
    private val repository: BattleRepository
) : ViewModel() {
    private val _logList = MutableStateFlow<List<TeamLog>>(listOf())
    val logList: StateFlow<List<TeamLog>> = _logList.asStateFlow()

    private val _memberList = MutableStateFlow<List<Member>>(listOf())
    val memberList: StateFlow<List<Member>> = _memberList.asStateFlow()

    private val _teamInfo = MutableStateFlow<AttackTeamState>(AttackTeamState.AttackTeamInit)
    val teamInfo: StateFlow<AttackTeamState> = _teamInfo.asStateFlow()

    private val _attackTeam = MutableStateFlow<AttackTeamState>(AttackTeamState.AttackTeamInit)
    val attackTeam: StateFlow<AttackTeamState> = _attackTeam.asStateFlow()

    fun getAttackTeam(teamId: Int) {
        viewModelScope.launch {
            runCatching {
                repository.getAttackTeam(teamId)
            }.onSuccess {
                _logList.value = it.logs
                _memberList.value = it.members
                _teamInfo.value = AttackTeamState.AttackTeamStatus(it)
            }.onFailure(Timber::e)
        }
    }

    fun postAttackTeam(teamId: Int) {
        viewModelScope.launch {
            runCatching {
                repository.postAttackTeam(teamId)
            }.onSuccess {
                _attackTeam.value = AttackTeamState.AttackTeamSuccess
            }.onFailure {
                _attackTeam.value = AttackTeamState.Failure("이용가능한 이용권이 없습니다")
            }
        }
    }
}

sealed class AttackTeamState() {
    object AttackTeamInit : AttackTeamState()
    data class AttackTeamStatus(val team: Team) : AttackTeamState()
    object AttackTeamSuccess : AttackTeamState()
    data class Failure(val message: String) : AttackTeamState()
}
