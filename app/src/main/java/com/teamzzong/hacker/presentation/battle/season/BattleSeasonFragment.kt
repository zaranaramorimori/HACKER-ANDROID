package com.teamzzong.hacker.presentation.battle.season

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
import com.teamzzong.hacker.databinding.FragmentBattleSeasonBinding
import com.teamzzong.hacker.presentation.battle.rank.BattleRankFragment
import com.teamzzong.hacker.presentation.gallery.MyPageNotiBalloonFactory
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.view.Spacer
import com.teamzzong.hacker.ui.view.itemDecoration
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BattleSeasonFragment :
    BindingFragment<FragmentBattleSeasonBinding>(R.layout.fragment_battle_season) {
    private val notificationBalloon by balloon<MyPageNotiBalloonFactory>()
    private var adapter: BattleSeasonAdapter? = null
    private val viewModel by viewModels<BattleSeasonViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
        initAdapter()
        observe()
    }

    private fun initEvent() {
        binding.ivAlarm.setOnSingleClickListener {
            binding.ivAlarm.showAlignBottom(notificationBalloon)
        }
    }

    private fun initAdapter() {
        adapter = BattleSeasonAdapter {
            val fragment = BattleRankFragment.newInstance(it.seasonId)
            parentFragmentManager.commit {
                replace(R.id.main_container, fragment).addToBackStack(null)
            }
        }

        binding.rvBattleSeason.adapter = adapter
        binding.rvBattleSeason.itemDecoration = Spacer {
            vertical { 10.dp }
        }
        viewModel.getSeason()
    }

    private fun observe() {
        viewModel.seasonList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (it.isEmpty()) {
                    binding.ivEmptySeason.isVisible = true
                    binding.rvBattleSeason.isVisible = false
                } else {
                    binding.ivEmptySeason.isVisible = false
                    binding.rvBattleSeason.isVisible = true
                }
                adapter?.submitList(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}