package com.teamzzong.hacker.presentation.gallery.content.gallery.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.FragmentFriendSearchResultBinding
import com.teamzzong.hacker.presentation.gallery.adapter.FriendSearchResultAdapter
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.fragment.toast
import com.teamzzong.hacker.ui.fragment.viewLifeCycle
import com.teamzzong.hacker.ui.fragment.viewLifeCycleScope
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FriendSearchResultFragment :
    BindingFragment<FragmentFriendSearchResultBinding>(R.layout.fragment_friend_search_result) {
    private val viewModel by activityViewModels<SearchFriendViewModel>()
    private var adapter: FriendSearchResultAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeEvent()
    }

    private fun initView() {
        adapter = FriendSearchResultAdapter(requireContext()).apply {
            setOnFriendSelectListener {
                viewModel.setFriend(it)
                setSelectedFriend(it)
            }
        }
        binding.listFriend.adapter = adapter
        binding.btnNext.setOnSingleClickListener {
            viewModel.addFriend()
        }
    }

    private fun observeEvent() {
        viewModel.friendList
            .flowWithLifecycle(viewLifeCycle)
            .filterNotNull()
            .onEach {
                adapter?.replaceList(it)
            }.launchIn(viewLifeCycleScope)
        viewModel.isFriendSelectComplete
            .flowWithLifecycle(viewLifeCycle)
            .onEach {
                binding.btnNext.isSelected = it
                binding.btnNext.isClickable = it
            }.launchIn(viewLifeCycleScope)
        viewModel.isFriendAddSuccess
            .flowWithLifecycle(viewLifeCycle)
            .onEach {
                if (it) requireActivity().finish()
                else toast("친구 추가 중 문제가 발생했습니다. 다시 시도해주시기 바랍니다.")
            }.launchIn(viewLifeCycleScope)
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}
