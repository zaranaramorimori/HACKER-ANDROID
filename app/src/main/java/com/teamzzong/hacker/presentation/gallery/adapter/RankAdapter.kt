package com.teamzzong.hacker.presentation.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teamzzong.hacker.databinding.ItemGalleryRankingBinding
import com.teamzzong.hacker.databinding.ItemGalleryRankingTopBinding
import com.teamzzong.hacker.domain.entity.user.OtherRankInfo
import com.teamzzong.hacker.presentation.gallery.content.gallery.detail.AddFriendActivity
import com.teamzzong.hacker.presentation.gallery.content.ranking.RankingViewModel
import com.teamzzong.hacker.presentation.home.UserDetailActivity
import com.teamzzong.hacker.shared.DECIMAL_FORMATTER
import com.teamzzong.hacker.ui.view.setOnSingleClickListener

abstract class RankViewHolder(
    rootBinding: ViewDataBinding
) : RecyclerView.ViewHolder(rootBinding.root) {
    abstract fun onBind(item: RankingViewModel.RankingUiState, position: Int = 0)
}

class RankAdapter(
    context: Context
) : RecyclerView.Adapter<RankViewHolder>() {
    private var itemList: List<RankingViewModel.RankingUiState> = emptyList()
    private val inflater by lazy { LayoutInflater.from(context) }
    private var myId: Int = -1

    class TopViewHolder(
        private val binding: ItemGalleryRankingTopBinding, private val myId: Int
    ) : RankViewHolder(binding) {
        override fun onBind(item: RankingViewModel.RankingUiState, position: Int) {
            (item as RankingViewModel.RankingUiState.Top).let {
                when (it.ranks.size) {
                    1 -> {
                        setFirstPlace(it.ranks[0])
                        hideSecondPlace()
                        hideThirdPlace()
                    }
                    2 -> {
                        setFirstPlace(it.ranks[0])
                        setSecondPlace(it.ranks[1])
                        hideThirdPlace()
                    }
                    else -> {
                        setFirstPlace(it.ranks[0])
                        setSecondPlace(it.ranks[1])
                        setThirdPlace(it.ranks[2])
                    }
                }
            }
        }

        private fun setFirstPlace(item: OtherRankInfo) {
            with(binding) {
                txtNameFirstPlace.text =
                    DECIMAL_FORMATTER.format(item.rank.commitCount) + " 커밋"
                txtRankFirstPlace.text = item.rank.nickname
                item.head?.let { imgRankFirstHair.load(it) }
                imgRankFirstHead.load(item.face)
                imgRankFirstHead.setOnSingleClickListener {
                    if (myId != item.rank.userId) {
                        binding.root.context.startActivity(
                            AddFriendActivity.getIntent(
                                binding.root.context, item.rank.userId
                            )
                        )
                    } else {
                        binding.root.context.startActivity(
                            UserDetailActivity.getIntent(
                                binding.root.context,
                            )
                        )
                    }
                }
            }
        }

        private fun setSecondPlace(item: OtherRankInfo) {
            with(binding) {
                txtNameSecondPlace.text =
                    DECIMAL_FORMATTER.format(item.rank.commitCount) + " 커밋"
                txtRankSecondPlace.text = item.rank.nickname
                item.head?.let { imgRankSecondHair.load(it) }
                imgRankSecondHead.load(item.face)
                imgRankSecondHead.setOnSingleClickListener {
                    if (myId != item.rank.userId) {
                        binding.root.context.startActivity(
                            AddFriendActivity.getIntent(
                                binding.root.context, item.rank.userId
                            )
                        )
                    } else {
                        binding.root.context.startActivity(
                            UserDetailActivity.getIntent(
                                binding.root.context,
                            )
                        )
                    }
                }
            }
        }

        private fun setThirdPlace(item: OtherRankInfo) {
            with(binding) {
                txtNameThirdPlace.text =
                    DECIMAL_FORMATTER.format(item.rank.commitCount) + " 커밋"
                txtRankThirdPlace.text = item.rank.nickname
                item.head?.let { imgRankThirdHair.load(it) }
                imgRankThirdHead.load(item.face)
                imgRankThirdHead.setOnSingleClickListener {
                    if (myId != item.rank.userId) {
                        binding.root.context.startActivity(
                            AddFriendActivity.getIntent(
                                binding.root.context, item.rank.userId
                            )
                        )
                    } else {
                        binding.root.context.startActivity(
                            UserDetailActivity.getIntent(
                                binding.root.context,
                            )
                        )
                    }
                }

            }
        }

        private fun hideThirdPlace() {
            with(binding) {
                imgRankThirdPlace.isVisible = false
                imgRankThirdHead.isVisible = false
                txtNameThirdPlace.isVisible = false
                txtRankThirdPlace.isVisible = false
                imgRankThirdHair.isVisible = false
            }
        }

        private fun hideSecondPlace() {
            with(binding) {
                imgRankSecondPlace.isVisible = false
                imgRankSecondHead.isVisible = false
                txtNameSecondPlace.isVisible = false
                txtRankSecondPlace.isVisible = false
                imgRankSecondHair.isVisible = false
            }
        }
    }

    class ElseViewHolder(
        private val binding: ItemGalleryRankingBinding, private val myId: Int
    ) : RankViewHolder(binding) {
        override fun onBind(item: RankingViewModel.RankingUiState, position: Int) {
            (item as RankingViewModel.RankingUiState.Else).let { item ->
                with(binding) {
                    txtRankTitle.text = "${position}등"
                    txtRankNickname.text = item.rank.rank.nickname
                    txtRankCommitCount.text =
                        DECIMAL_FORMATTER.format(item.rank.rank.commitCount) + " 커밋"
                    imgRankHead.load(item.rank.face)
                    imgRankHair.load(item.rank.head)
                    if (myId != item.rank.rank.userId) {
                        root.setOnSingleClickListener {
                            binding.root.context.startActivity(
                                AddFriendActivity.getIntent(
                                    binding.root.context, item.rank.rank.userId
                                )
                            )
                        }
                    } else {
                        root.setOnSingleClickListener {
                            binding.root.context.startActivity(
                                UserDetailActivity.getIntent(
                                    binding.root.context,
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun replaceList(items: List<RankingViewModel.RankingUiState>) {
        itemList = items.toList()
        notifyDataSetChanged()
    }

    fun setMyId(userId: Int) {
        myId = userId
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is RankingViewModel.RankingUiState.Top -> TOP
            else -> ELSE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        return when (viewType) {
            TOP -> TopViewHolder(
                ItemGalleryRankingTopBinding.inflate(inflater, parent, false), myId
            )
            else -> ElseViewHolder(ItemGalleryRankingBinding.inflate(inflater, parent, false), myId)
        }
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.onBind(itemList[position], position + 3)
    }

    override fun getItemCount() = itemList.size

    fun findPositionOf(userId: Int): Int {
        return itemList.indexOfFirst { it.findUserById(userId) }
    }

    companion object {
        private const val TOP = 1000
        private const val ELSE = 2000
    }
}
