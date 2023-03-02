package com.teamzzong.hacker.presentation.battle.season

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamzzong.hacker.databinding.ItemSeasonBinding
import com.teamzzong.hacker.domain.entity.battle.Season

class BattleSeasonAdapter(private val itemClick: (Season) -> (Unit)) :
    ListAdapter<Season, BattleSeasonAdapter.BattleSeasonViewHolder>(DIFFUTIL) {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattleSeasonViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding =
            ItemSeasonBinding.inflate(inflater, parent, false)
        return BattleSeasonViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: BattleSeasonViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class BattleSeasonViewHolder(
        private val binding: ItemSeasonBinding,
        private val itemClick: (Season) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(season: Season) {
            binding.season = season

            binding.root.setOnClickListener {
                itemClick(season)
            }
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<Season>() {
            override fun areItemsTheSame(
                oldItem: Season,
                newItem: Season
            ): Boolean {
                return oldItem.seasonId == newItem.seasonId
            }

            override fun areContentsTheSame(
                oldItem: Season,
                newItem: Season
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}