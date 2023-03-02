package com.teamzzong.hacker.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

object BindingConversions {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {
        imageView.load(url)
    }
}