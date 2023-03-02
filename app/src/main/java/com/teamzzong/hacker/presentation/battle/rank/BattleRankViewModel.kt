package com.teamzzong.hacker.presentation.battle.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.battle.MyTeam
import com.teamzzong.hacker.domain.entity.battle.Teams
import com.teamzzong.hacker.domain.repository.battle.BattleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BattleRankViewModel @Inject constructor(
    private val repository: BattleRepository
) : ViewModel() {
    private val _rankList = MutableStateFlow<List<TeamState>>(listOf())
    val rankList: StateFlow<List<TeamState>> = _rankList.asStateFlow()

    private val _myTeamRank = MutableStateFlow<TeamState>(TeamState.Init)
    val myTeamRank = _myTeamRank.asStateFlow()

    fun getRank(seasonId: Int) {
        viewModelScope.launch {
            runCatching {
                repository.getBattleRank(seasonId)
            }.onSuccess {
                _myTeamRank.value = TeamState.MyTeamRank(it.myTeam)
                val firstRank = TeamState.FirstTeam(it.Teams[0])
                val otherRank = it.Teams.drop(1).map { TeamState.Else(it) }
                _rankList.value = listOf<TeamState>(firstRank) + otherRank
            }.onFailure(Timber::e)
        }
    }

    sealed interface TeamState {
        fun findTeamById(teamId: Int): Boolean

        object Init : TeamState {
            override fun findTeamById(userId: Int): Boolean {
                return false
            }
        }

        data class MyTeamRank(val rank: MyTeam) : TeamState {
            override fun findTeamById(teamId: Int): Boolean {
                return rank.teamId == teamId
            }
        }

        data class FirstTeam(val rank: Teams) : TeamState {
            override fun findTeamById(teamId: Int): Boolean {
                return rank.teamId == teamId
            }
        }

        data class Else(val rank: Teams) : TeamState {
            override fun findTeamById(teamId: Int): Boolean {
                return rank.teamId == teamId
            }
        }
    }
}