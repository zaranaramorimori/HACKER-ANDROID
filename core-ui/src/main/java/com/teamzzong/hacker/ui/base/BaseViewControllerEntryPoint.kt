package com.teamzzong.hacker.ui.base

import com.teamzzong.hacker.ui.design.ResolutionMetrics
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BaseViewControllerEntryPoint {
    fun resolutionMetrics(): ResolutionMetrics
}
