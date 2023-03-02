package com.teamzzong.hacker.presentation.battle.rank

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.skydoves.balloon.balloon
import com.skydoves.balloon.showAlignBottom
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.FragmentBattleRankBinding
import com.teamzzong.hacker.presentation.battle.attack.BattleAttackFragment
import com.teamzzong.hacker.presentation.gallery.MyPageNotiBalloonFactory
import com.teamzzong.hacker.shared.DECIMAL_FORMATTER
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.intent.intArgs
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BattleRankFragment :
    BindingFragment<FragmentBattleRankBinding>(R.layout.fragment_battle_rank) {
    private val notificationBalloon by balloon<MyPageNotiBalloonFactory>()
    private val seasonId by intArgs()
    private var adapter: BattleRankAdapter? = null
    private val viewModel by viewModels<BattleRankViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        initAdapter()
        observe()
    }

    private fun initEvent() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.ivAlarm.setOnSingleClickListener {
            binding.ivAlarm.showAlignBottom(notificationBalloon)
        }
    }

    private fun initAdapter() {
        adapter = BattleRankAdapter {
            val fragment = BattleAttackFragment.newInstance(it.teamId)
            parentFragmentManager.commit {
                replace(R.id.main_container, fragment).addToBackStack(null)
            }
        }
        binding.rvBattleTeam.adapter = adapter
        viewModel.getRank(seasonId)
    }

    private fun observe() {
        viewModel.rankList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (it.isEmpty()) {
                    binding.ivEmptyRank.isVisible = true
                    binding.clBattleTeam.isVisible = false
                } else {
                    binding.ivEmptyRank.isVisible = false
                    binding.clBattleTeam.isVisible = true
                    adapter?.setItems(it)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.myTeamRank.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .filterIsInstance<BattleRankViewModel.TeamState.MyTeamRank>()
            .onEach {
                binding.btnRankMe.isVisible = it.rank != null
                with(binding) {
                    tvRankNickname.text = it.rank.name
                    tvRankTitle.text = "${it.rank.rank}등"
                    tvRankCommitCount.text =
                        "${DECIMAL_FORMATTER.format(it.rank.commitCount)} 커밋"
                    btnRankMe.setOnSingleClickListener { _ ->
                        val position = adapter?.findPositionOf(it.rank.teamId) ?: -1
                        if (position != -1) {
                            viewLifecycleOwner.lifecycleScope.launch {
                                binding.rvBattleTeam.smoothScrollToPosition(position)
                            }
                        }
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(seasonId: Int) =
            BattleRankFragment().apply {
                arguments = Bundle().apply {
                    putInt("seasonId", seasonId)
                }
            }
    }
}