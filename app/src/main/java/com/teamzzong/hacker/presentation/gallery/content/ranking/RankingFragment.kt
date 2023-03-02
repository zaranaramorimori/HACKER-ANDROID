package com.teamzzong.hacker.presentation.gallery.content.ranking

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout.VERTICAL
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.simform.refresh.SSPullToRefreshLayout
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.FragmentRankingBinding
import com.teamzzong.hacker.presentation.gallery.adapter.RankAdapter
import com.teamzzong.hacker.shared.DECIMAL_FORMATTER
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.fragment.drawableOf
import com.teamzzong.hacker.ui.fragment.viewLifeCycle
import com.teamzzong.hacker.ui.fragment.viewLifeCycleScope
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RankingFragment : BindingFragment<FragmentRankingBinding>(R.layout.fragment_ranking) {
    private val viewModel by viewModels<RankingViewModel>()
    private var adapter: RankAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutRefresh.apply {
            setLottieAnimation("head_spin.json")
            setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
            setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
            setRefreshStyle(SSPullToRefreshLayout.RefreshStyle.NORMAL)
            setOnRefreshListener { viewModel.setRankScreen() }
        }
        adapter = RankAdapter(requireContext())
        binding.listTotalRank.adapter = adapter
        binding.listTotalRank.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                VERTICAL
            ).apply { drawableOf(R.drawable.divider_rank_list)?.let { setDrawable(it) } }
        )
        viewModel.myRank
            .flowWithLifecycle(viewLifeCycle)
            .filterIsInstance<RankingViewModel.RankingUiState.My>()
            .onEach {
                binding.btnRankMe.isVisible = it.rank != null
                it.rank?.let { rank ->
                    adapter?.setMyId(rank.userId)
                    with(binding) {
                        txtRankNickname.text = rank.nickname
                        txtRankTitle.text = "${rank.rank}등"
                        txtRankCommitCount.text = "${DECIMAL_FORMATTER.format(rank.commitCount)} 커밋"
                        btnRankMe.setOnSingleClickListener {
                            val position = adapter?.findPositionOf(rank.userId) ?: -1
                            if (position != -1) {
                                viewLifecycleOwner.lifecycleScope.launch {
                                    binding.listTotalRank.smoothScrollToPosition(position)
                                }
                            }
                        }
                    }
                }
            }.launchIn(viewLifeCycleScope)

        viewModel.totalRank
            .flowWithLifecycle(viewLifeCycle)
            .onEach { adapter?.replaceList(it) }
            .launchIn(viewLifeCycleScope)

        viewModel.updateEvent
            .flowWithLifecycle(viewLifeCycle)
            .onEach { binding.layoutRefresh.setRefreshing(false) }
            .launchIn(viewLifeCycleScope)
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}
