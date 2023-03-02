package com.teamzzong.hacker.presentation.gallery.content.gallery

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.FragmentGalleryBinding
import com.teamzzong.hacker.presentation.gallery.adapter.GalleryAdapter
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.fragment.viewLifeCycle
import com.teamzzong.hacker.ui.fragment.viewLifeCycleScope
import com.teamzzong.hacker.util.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val viewModel by viewModels<GalleryViewModel>()
    private var adapter: GalleryAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GalleryAdapter(requireContext())
        binding.rvMypageGallery.adapter = adapter
        viewModel.friends
            .flowWithLifecycle(viewLifeCycle)
            .onEach {
                adapter?.submitList(it)
            }.launchIn(viewLifeCycleScope)
        binding.rvMypageGallery.addItemDecoration(GalleryItemDecoration())
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshList()
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}

class GalleryItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 4.dp
        outRect.bottom = 4.dp
        outRect.left = 6.dp
        outRect.right = 6.dp
    }
}
