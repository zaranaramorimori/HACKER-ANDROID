package com.teamzzong.hacker.presentation.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamzzong.hacker.databinding.ItemSearchFriendAlreadyBinding
import com.teamzzong.hacker.databinding.ItemSearchFriendBinding
import com.teamzzong.hacker.domain.entity.user.GithubUser

abstract class FriendSearchResultViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun onBind(item: GithubUser)
}

class FriendSearchResultAdapter(
    context: Context
) : RecyclerView.Adapter<FriendSearchResultViewHolder>() {
    private var itemList: List<GithubUser> = emptyList()
    private val inflater by lazy { LayoutInflater.from(context) }
    private var onFriendSelectListener: OnFriendSelectListener? = null
    private var selectedItem: GithubUser? = null

    fun interface OnFriendSelectListener {
        fun onSelect(item: GithubUser)
    }

    class AlreadyFriendViewHolder(
        private val binding: ItemSearchFriendAlreadyBinding
    ) : FriendSearchResultViewHolder(binding) {
        override fun onBind(item: GithubUser) {
            binding.imgProfile.load(item.profileImageUrl)
            binding.txtUsername.text = item.nickName
            binding.txtGithubNickname.text = item.userName
        }
    }

    inner class ResultViewHolder(
        private val binding: ItemSearchFriendBinding,
        private val listener: OnFriendSelectListener?
    ) : FriendSearchResultViewHolder(binding) {
        override fun onBind(item: GithubUser) {
            binding.btnSelected.isSelected =
                if (selectedItem != null) item == selectedItem else false
            binding.imgProfile.load(item.profileImageUrl)
            binding.txtUsername.text = item.nickName
            binding.txtGithubNickname.text = item.userName
            binding.btnSelected.setOnClickListener {
                listener?.onSelect(item)
            }
        }
    }

    fun replaceList(newList: List<GithubUser>) {
        itemList = newList.toList()
        notifyDataSetChanged()
    }

    fun setOnFriendSelectListener(block: OnFriendSelectListener) {
        onFriendSelectListener = block
    }

    fun setSelectedFriend(item: GithubUser) {
        selectedItem = item
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =
        if (itemList[position].isFriend) ALREADY_FRIEND else RESULT

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendSearchResultViewHolder {
        return when (viewType) {
            ALREADY_FRIEND -> {
                AlreadyFriendViewHolder(
                    ItemSearchFriendAlreadyBinding.inflate(inflater, parent, false)
                )
            }
            else -> {
                ResultViewHolder(
                    ItemSearchFriendBinding.inflate(inflater, parent, false),
                    onFriendSelectListener
                )
            }
        }
    }

    override fun onBindViewHolder(holder: FriendSearchResultViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    companion object {
        private const val ALREADY_FRIEND = 0
        private const val RESULT = 1
    }
}
