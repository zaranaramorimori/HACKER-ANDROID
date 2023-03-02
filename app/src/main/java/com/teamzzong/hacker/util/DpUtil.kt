package com.teamzzong.hacker.util

import com.teamzzong.hacker.App

val Number.dp: Int
    get() = App.resolutionMetrics.toPixel(this.toInt())
