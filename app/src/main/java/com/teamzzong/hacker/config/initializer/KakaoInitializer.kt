package com.teamzzong.hacker.config.initializer

import android.content.Context
import androidx.startup.Initializer
import com.kakao.sdk.common.KakaoSdk

class KakaoInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        KakaoSdk.init(context, "181601b347f55aefd2e3060ccb2766fc")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}