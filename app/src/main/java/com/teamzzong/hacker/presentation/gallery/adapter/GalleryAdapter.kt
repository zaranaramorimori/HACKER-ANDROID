package com.teamzzong.hacker.presentation.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamzzong.hacker.databinding.ItemGalleryAddBinding
import com.teamzzong.hacker.databinding.ItemGalleryUserBinding
import com.teamzzong.hacker.presentation.gallery.content.gallery.detail.AddFriendActivity
import com.teamzzong.hacker.presentation.gallery.content.gallery.search.SearchFriendActivity
import com.teamzzong.hacker.presentation.gallery.model.GalleryUiState
import com.teamzzong.hacker.ui.view.ItemDiffCallback
import com.teamzzong.hacker.ui.view.setOnSingleClickListener

class GalleryAdapter(context: Context) : ListAdapter<GalleryUiState, GalleryViewHolder>(
    ItemDiffCallback<GalleryUiState>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new ->
            when {
                old is GalleryUiState.User && new is GalleryUiState.User -> old.id == new.id
                old is GalleryUiState.Add && new is GalleryUiState.Add -> true
                else -> false
            }
        }
    )
) {
    private val layoutInflater by lazy { LayoutInflater.from(context) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = when (viewType) {
            ADD -> ItemGalleryAddBinding.inflate(layoutInflater)
            USER -> ItemGalleryUserBinding.inflate(layoutInflater)
            else -> throw IllegalStateException("Gallery Adapter: wrong viewType $viewType")
        }
        return when (binding) {
            is ItemGalleryAddBinding -> AddViewHolder(binding)
            is ItemGalleryUserBinding -> UserViewHolder(binding)
            else -> throw IllegalStateException("Gallery Adapter: wrong viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class AddViewHolder(private val binding: ItemGalleryAddBinding) : GalleryViewHolder(binding) {
        override fun onBind(item: GalleryUiState) {
            binding.root.setOnSingleClickListener {
                binding.root.context.startActivity(
                    SearchFriendActivity.getIntent(binding.root.context)
                )
            }
        }
    }

    class UserViewHolder(private val binding: ItemGalleryUserBinding) : GalleryViewHolder(binding) {
        override fun onBind(item: GalleryUiState) {
            (item as GalleryUiState.User).let { user ->
                binding.root.setOnSingleClickListener {
                    binding.root.context.startActivity(
                        AddFriendActivity.getIntent(binding.root.context, user.id)
                    )
                }
                binding.txtGalleryNickname.text = user.nickName
                binding.imgGalleryUserFace.load(user.face)
                binding.imgGalleryUserHair.load(user.head)
            }
        }
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> ADD
        else -> USER
    }

    companion object {
        private const val ADD = 0
        private const val USER = 1
    }
}

abstract class GalleryViewHolder(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun onBind(item: GalleryUiState)
}
