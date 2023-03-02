package com.teamzzong.hacker.presentation.battle.rank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.ItemFirstRankBinding
import com.teamzzong.hacker.databinding.ItemRankBinding
import com.teamzzong.hacker.domain.entity.battle.Teams
import java.text.DecimalFormat

abstract class CreateRankViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: BattleRankViewModel.TeamState, position: Int = 0)
}

class BattleRankAdapter(private val itemClick: (Teams) -> (Unit)) :
    RecyclerView.Adapter<CreateRankViewHolder>() {
    private var teams : List<BattleRankViewModel.TeamState> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateRankViewHolder {
        return when (viewType) {
            BATTLE_FIRST_RANK -> {
                val binding = binding<ItemFirstRankBinding>(parent, R.layout.item_first_rank)
                FirstRankViewHolder(binding, itemClick)
            }
            else -> {
                val binding = binding<ItemRankBinding>(parent, R.layout.item_rank)
                RankViewHolder(binding, itemClick)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (teams[position]) {
            is BattleRankViewModel.TeamState.FirstTeam -> BATTLE_FIRST_RANK
            else -> ELSE
        }
    }

    override fun onBindViewHolder(holder: CreateRankViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    override fun getItemCount(): Int = teams.size

    fun setItems(newItems: List<BattleRankViewModel.TeamState>) {
        teams = newItems.toList()
        notifyDataSetChanged()
    }

    class FirstRankViewHolder(
        private val binding: ItemFirstRankBinding,
        private val itemClick: (Teams) -> (Unit)
    ) : CreateRankViewHolder(binding) {
        override fun bind(item: BattleRankViewModel.TeamState, position: Int) {
            (item as BattleRankViewModel.TeamState.FirstTeam).let {
                val dec = DecimalFormat("#,###")
                binding.apply {
                    tvTeamName.text = item.rank.name
                    tvCommit.text = "${dec.format(item.rank.commitCount)} 커밋"
                    ivFace.load(item.rank.face)
                    ivHair.load(item.rank.head)
                }
                binding.root.setOnClickListener {
                    itemClick(item.rank)
                }
            }
        }
    }

    class RankViewHolder(
        private val binding: ItemRankBinding,
        private val itemClick: (Teams) -> (Unit)
    ) : CreateRankViewHolder(binding) {
        override fun bind(item: BattleRankViewModel.TeamState, position: Int) {
            (item as BattleRankViewModel.TeamState.Else).let {
                val dec = DecimalFormat("#,###")
                binding.apply {
                    tvRank.text = "${it.rank.rank}등"
                    tvCommit.text = "${dec.format(item.rank.commitCount)} 커밋"
                    tvTeamName.text = item.rank.name
                    ivFace.load(item.rank.face)
                    ivHair.load(item.rank.head)
                }

                binding.root.setOnClickListener {
                    itemClick(item.rank)
                }
            }
        }
    }

    private fun <T : ViewDataBinding> binding(parent: ViewGroup, @LayoutRes layoutRes: Int) =
        DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutRes, parent, false)


    fun findPositionOf(teamId: Int): Int {
        return teams.indexOfFirst { it.findTeamById(teamId) }
    }

    companion object {
        const val BATTLE_FIRST_RANK = 1
        const val ELSE = 2
    }
}