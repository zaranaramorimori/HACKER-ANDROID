package com.teamzzong.hacker.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import kotlin.math.min

@SuppressLint("AppCompatCustomView")
class CircleImageView : ImageView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private var radius = 0.0f
    private var borderColor = Color.BLACK
    private var borderWidth = 10f

    private fun init() {
        setScaleType()
    }

    private fun setScaleType() {
        super.setScaleType(ScaleType.CENTER_CROP)
    }

    fun setBorderStyle(width: Float, color: Int) {
        this.borderWidth = width
        this.borderColor = color
    }

    override fun onDraw(canvas: Canvas?) {
        val path = Path()
        val rect = RectF(0f, 0f, this.width.toFloat(), this.height.toFloat())
        radius = min(this.width.toFloat() / 2.0f, this.height.toFloat() / 2.0f)
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas!!.clipPath(path)

        super.onDraw(canvas)

        val point = Paint()
        with(point) {
            strokeWidth = borderWidth
            color = borderColor
            isAntiAlias = true
            style = Paint.Style.STROKE
            canvas?.drawCircle(
                measuredWidth / 2.toFloat(),
                measuredHeight / 2.toFloat(),
                (measuredWidth / 2 - 5).toFloat(),
                this
            )
        }
    }
}