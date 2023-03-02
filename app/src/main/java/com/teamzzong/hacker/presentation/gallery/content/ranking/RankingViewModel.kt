package com.teamzzong.hacker.presentation.gallery.content.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamzzong.hacker.domain.entity.user.OtherRankInfo
import com.teamzzong.hacker.domain.entity.user.Rank
import com.teamzzong.hacker.domain.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    sealed interface RankingUiState {
        object RankingInit : RankingUiState {
            override fun findUserById(userId: Int): Boolean {
                return false
            }
        }

        data class Top(val ranks: List<OtherRankInfo>) : RankingUiState {
            override fun findUserById(userId: Int): Boolean {
                return ranks.find { it.rank.userId == userId } != null
            }
        }

        data class Else(val rank: OtherRankInfo) : RankingUiState {
            override fun findUserById(userId: Int): Boolean {
                return rank.rank.userId == userId
            }
        }

        data class My(val rank: Rank?) : RankingUiState {
            override fun findUserById(userId: Int): Boolean {
                return rank?.userId == userId
            }
        }

        fun findUserById(userId: Int): Boolean
    }

    init {
        setRankScreen()
    }

    private val _totalRank: MutableStateFlow<List<RankingUiState>> =
        MutableStateFlow(emptyList())
    val totalRank = _totalRank.asStateFlow()
    private val _myRank: MutableStateFlow<RankingUiState> =
        MutableStateFlow(RankingUiState.RankingInit)
    val myRank = _myRank.asStateFlow()
    val updateEvent = MutableSharedFlow<Unit>()

    fun setRankScreen() {
        viewModelScope.launch {
            runCatching { repository.getTotalRank() }
                .onSuccess {
                    _myRank.value = RankingUiState.My(it.myRank)
                    val topRank = RankingUiState.Top(it.otherRankInfo.take(3))
                    val otherRank = it.otherRankInfo.drop(3).map { RankingUiState.Else(it) }
                    _totalRank.value = listOf<RankingUiState>(topRank) + otherRank
                    updateEvent.emit(Unit)
                }.onFailure(Timber::e)
        }
    }
}
