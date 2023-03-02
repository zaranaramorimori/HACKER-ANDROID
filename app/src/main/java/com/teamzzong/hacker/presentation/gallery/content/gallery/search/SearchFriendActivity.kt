package com.teamzzong.hacker.presentation.gallery.content.gallery.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.ActivitySearchFriendBinding
import com.teamzzong.hacker.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFriendActivity :
    BindingActivity<ActivitySearchFriendBinding>(R.layout.activity_search_friend) {
    private val viewModel by viewModels<SearchFriendViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.container_fragment, SearchFriendFragment())
            }
        }
        initView()
        observeEvent()
    }

    private fun observeEvent() {
        viewModel.friendList
            .map { it != null }
            .onEach {
                supportFragmentManager.commit {
                    replace(
                        R.id.container_fragment,
                        if (it) FriendSearchResultFragment() else SearchFriendFragment()
                    )
                }
            }.launchIn(lifecycleScope)
    }

    private fun initView() {
        binding.btnBack.setOnClickListener { finish() }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SearchFriendActivity::class.java)
    }
}