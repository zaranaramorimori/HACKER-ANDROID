package com.teamzzong.hacker.presentation.battle.attack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamzzong.hacker.databinding.ItemMemberBinding
import com.teamzzong.hacker.domain.entity.battle.Member
import com.teamzzong.hacker.presentation.gallery.content.gallery.detail.AddFriendActivity
import com.teamzzong.hacker.ui.view.setOnSingleClickListener

class BattleMemberAdapter :
    ListAdapter<Member, BattleMemberAdapter.BattleMemberViewHolder>(DIFFUTIL) {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattleMemberViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding =
            ItemMemberBinding.inflate(inflater, parent, false)
        return BattleMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BattleMemberViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class BattleMemberViewHolder(
        private val binding: ItemMemberBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(member: Member) {
            binding.data = member
            binding.ivHair.load(member.head)
            binding.ivFace.load(member.face)
            binding.root.setOnSingleClickListener {
                binding.root.context.startActivity(
                    AddFriendActivity.getIntent(binding.root.context, member.userId)
                )
            }
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<Member>() {
            override fun areItemsTheSame(
                oldItem: Member,
                newItem: Member
            ): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(
                oldItem: Member,
                newItem: Member
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
