package com.teamzzong.hacker.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.hilt.android.EntryPointAccessors

abstract class BindingFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {
    private var _binding: T? = null
    protected val binding: T
        get() = requireNotNull(_binding) { "binding object is not initialized" }
    private val metrics by lazy {
        EntryPointAccessors.fromApplication(
            requireContext().applicationContext,
            BaseViewControllerEntryPoint::class.java
        ).resolutionMetrics()
    }
    val Number.pixel: Int
        @Px get() = metrics.toDP(this.toInt())

    val Number.dp: Int
        get() = metrics.toPixel(this.toInt())

    val Number.pixelFloat: Float
        @Px get() = metrics.toDP(this.toInt()).toFloat()

    val Number.dpFloat: Float
        get() = metrics.toPixel(this.toInt()).toFloat()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
