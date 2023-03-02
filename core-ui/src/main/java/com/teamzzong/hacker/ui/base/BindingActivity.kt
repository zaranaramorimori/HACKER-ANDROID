package com.teamzzong.hacker.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.EntryPointAccessors

abstract class BindingActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    protected lateinit var binding: T
    private val metrics by lazy {
        EntryPointAccessors.fromApplication(
            this.applicationContext,
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
    }
}
