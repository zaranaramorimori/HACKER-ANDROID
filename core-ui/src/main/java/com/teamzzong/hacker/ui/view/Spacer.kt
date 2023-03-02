package com.teamzzong.hacker.ui.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

typealias Value = () -> Int

var RecyclerView.itemDecoration: RecyclerView.ItemDecoration
    set(value) = this.addItemDecoration(value)
    get() = throw IllegalArgumentException("This is only property for set ItemDecoration!")

fun Spacer(block: SpacerBuilder.() -> Unit) = SpacerBuilder()
    .apply(block)
    .build()
    .toItemDecoration()

data class Spacer(
    private val vertical: Int,
    private val horizontal: Int,
    private val start: Int,
    private val end: Int,
    private val top: Int,
    private val bottom: Int
) {
    fun toItemDecoration(): RecyclerView.ItemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = if (vertical != DEFAULT_SPACER_VALUE) vertical else top
            outRect.bottom = if (vertical != DEFAULT_SPACER_VALUE) vertical else bottom
            outRect.left = if (horizontal != DEFAULT_SPACER_VALUE) horizontal else start
            outRect.right = if (horizontal != DEFAULT_SPACER_VALUE) horizontal else end
        }
    }
}

class SpacerBuilder {
    private var vertical: Int = DEFAULT_SPACER_VALUE
    private var horizontal: Int = DEFAULT_SPACER_VALUE
    private var start: Int = DEFAULT_SPACER_VALUE
    private var end: Int = DEFAULT_SPACER_VALUE
    private var top: Int = DEFAULT_SPACER_VALUE
    private var bottom: Int = DEFAULT_SPACER_VALUE

    fun vertical(block: Value) {
        vertical = block()
    }

    fun horizontal(block: Value) {
        horizontal = block()
    }

    fun start(block: Value) {
        start = block()
    }

    fun end(block: Value) {
        end = block()
    }

    fun top(block: Value) {
        top = block()
    }

    fun bottom(block: Value) {
        bottom = block()
    }

    fun build() = Spacer(vertical, horizontal, start, end, top, bottom)
}

internal const val DEFAULT_SPACER_VALUE = 0
