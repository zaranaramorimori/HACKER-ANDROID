package com.teamzzong.hacker

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import leakcanary.LeakCanary
import okhttp3.OkHttpClient

object FlipperInitializer {
    private val flipperNetworkPlugin = NetworkFlipperPlugin()

    fun init(app: Application) {
        LeakCanary.config = LeakCanary.config.copy(
            onHeapAnalyzedListener = FlipperLeakListener()
        )
        SoLoader.init(app, false)
        val client = AndroidFlipperClient.getInstance(app)
        client.addPlugin(InspectorFlipperPlugin(app, DescriptorMapping.withDefaults()))
        client.addPlugin(flipperNetworkPlugin)
        client.addPlugin(LeakCanary2FlipperPlugin())
        client.addPlugin(SharedPreferencesFlipperPlugin(app, "HACKER_DATA"))
        client.start()
    }

    fun addFlipperNetworkPlugin(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder.addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
    }
}