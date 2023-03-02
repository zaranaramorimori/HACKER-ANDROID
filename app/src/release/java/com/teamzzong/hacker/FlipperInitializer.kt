package com.teamzzong.hacker

import android.app.Application
import okhttp3.OkHttpClient

object FlipperInitializer {
    fun init(app: Application) {}
    fun addFlipperNetworkPlugin(builder: OkHttpClient.Builder) = builder
}
