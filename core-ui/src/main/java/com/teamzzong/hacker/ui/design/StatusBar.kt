package com.teamzzong.hacker.ui.design

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.teamzzong.hacker.ui.context.colorOf


fun Activity.statusBarColorOf(@ColorRes resId: Int) {
    window?.statusBarColor = colorOf(resId)
}

fun Fragment.statusBarColorOf(@ColorRes resId: Int) {
    requireActivity().statusBarColorOf(resId)
}

