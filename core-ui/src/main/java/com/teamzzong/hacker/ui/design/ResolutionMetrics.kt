package com.teamzzong.hacker.ui.design

import androidx.annotation.Px

interface ResolutionMetrics {
    @Px
    fun toPixel(dp: Int): Int
    fun toDP(@Px pixel: Int): Int
}