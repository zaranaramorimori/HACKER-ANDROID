package com.teamzzong.hacker.design

import android.app.Application
import androidx.annotation.Px
import com.teamzzong.hacker.ui.design.ResolutionMetrics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.roundToInt

class ResolutionMetricsImpl @Inject constructor(
    @ApplicationContext private val application: Application
) : ResolutionMetrics {
    private val displayMetrics
        get() = application.resources.displayMetrics

    val screenWidth
        get() = displayMetrics.widthPixels

    val screenHeight
        get() = displayMetrics.heightPixels

    val screenShort
        get() = screenWidth.coerceAtMost(screenHeight)

    val screenLong
        get() = screenWidth.coerceAtLeast(screenHeight)

    @Px
    override fun toPixel(dp: Int) = (dp * displayMetrics.density).roundToInt()
    override fun toDP(@Px pixel: Int) = (pixel / displayMetrics.density).roundToInt()
}
