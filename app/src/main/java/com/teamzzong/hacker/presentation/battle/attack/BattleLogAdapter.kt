package com.teamzzong.hacker.presentation.battle.attack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamzzong.hacker.databinding.ItemLogBinding
import com.teamzzong.hacker.databinding.ItemLogDetailBinding
import com.teamzzong.hacker.domain.entity.battle.TeamLog
import com.teamzzong.hacker.ui.view.Spacer
import com.teamzzong.hacker.ui.view.itemDecoration


class BattleOuterLogAdapter :
    RecyclerView.Adapter<BattleOuterLogAdapter.BattleOuterLogViewHolder>() {
    private lateinit var inflater: LayoutInflater
    private val teamLogs = mutableListOf<TeamLog>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattleOuterLogViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding =
            ItemLogBinding.inflate(inflater, parent, false)
        return BattleOuterLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BattleOuterLogViewHolder, position: Int) {
        holder.onBind(teamLogs[position])
    }

    override fun getItemCount(): Int = teamLogs.size

    fun setItems(newItems: List<TeamLog>) {
        teamLogs.clear()
        teamLogs.addAll(newItems)
        notifyDataSetChanged()
    }

    class BattleOuterLogViewHolder(private val binding: ItemLogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(teamLogs: TeamLog) {
            binding.model = teamLogs

            binding.rvTeamLog.adapter = BattleInnerLogAdapter(teamLogs.content)
            binding.rvTeamLog.itemDecoration = Spacer {
                vertical { 20 }
            }
        }
    }
}

class BattleInnerLogAdapter(private val itemList: MutableList<String>) :
    RecyclerView.Adapter<BattleInnerLogAdapter.BattleInnerLogViewHolder>() {
    private lateinit var inflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattleInnerLogViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding =
            ItemLogDetailBinding.inflate(inflater, parent, false)
        return BattleInnerLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BattleInnerLogViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class BattleInnerLogViewHolder(private val binding: ItemLogDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(logDetail: String) {
            binding.tvLog.text = logDetail
        }
    }
}

