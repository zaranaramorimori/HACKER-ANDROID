package com.teamzzong.hacker.presentation.gallery.content.gallery.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.FragmentSearchFriendBinding
import com.teamzzong.hacker.shared.SingleAlertDialog
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.fragment.viewLifeCycle
import com.teamzzong.hacker.ui.fragment.viewLifeCycleScope
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFriendFragment :
    BindingFragment<FragmentSearchFriendBinding>(R.layout.fragment_search_friend) {
    private val viewModel by activityViewModels<SearchFriendViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeData()
    }

    private fun subscribeData() {
        viewModel.isFriendAddAvailable
            .flowWithLifecycle(viewLifeCycle)
            .onEach {
                binding.txtSearchFriendTitle.text = it.title
                binding.txtSearchFriendDescription.text = it.content
                binding.ivClear.setImageResource(it.resId)
                binding.ivClear.isEnabled = it != SearchFriendViewModel.Status.IS_ME
                binding.btnNext.isSelected = it == SearchFriendViewModel.Status.AVAILABLE
                binding.btnNext.isClickable = it == SearchFriendViewModel.Status.AVAILABLE
            }.launchIn(viewLifeCycleScope)
        viewModel.errorStatus
            .flowWithLifecycle(viewLifeCycle)
            .onEach {
                SingleAlertDialog()
                    .apply {
                        setTitle(it.errorMessage)
                        setOnConfirmClickListener { dismiss() }
                    }.show(parentFragmentManager, "ErrorSearchDialog")
            }.launchIn(viewLifeCycleScope)
    }

    private fun initView() {
        binding.ivClear.setOnClickListener { binding.editUserName.setText("") }
        binding.editUserName.setOnFocusChangeListener { _, hasFocus ->
            viewModel.setFocusState(hasFocus)
        }
        binding.btnNext.setOnSingleClickListener {
            viewModel.searchUser()
        }
    }
}
