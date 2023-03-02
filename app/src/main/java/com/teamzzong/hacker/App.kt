package com.teamzzong.hacker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.ui.design.ResolutionMetrics
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var metrics: ResolutionMetrics

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var hackerDataStore: HackerDataStore

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        FlipperInitializer.init(this)
        resolutionMetrics = metrics
        hackerDataStore.versionCode = BuildConfig.VERSION_CODE
    }

    companion object {
        lateinit var resolutionMetrics: ResolutionMetrics
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
    }
}
