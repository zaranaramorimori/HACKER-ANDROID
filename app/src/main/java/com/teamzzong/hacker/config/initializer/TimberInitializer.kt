package com.teamzzong.hacker.config.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.teamzzong.hacker.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Timber.plant(if (BuildConfig.DEBUG) HackerDebugTree() else HackerTree())
    }

    private class HackerDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
            return "Hacker://${element.fileName}:${element.lineNumber}#${element.methodName}"
        }
    }

    private class HackerTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            when (priority) {
                Log.WARN, Log.ERROR -> t?.let {
                    val newThrowable = Throwable().initCause(t).apply {
                        stackTrace = Thread.currentThread().stackTrace
                    }
                    FirebaseCrashlytics.getInstance().recordException(newThrowable)
                }
                else -> FirebaseCrashlytics.getInstance().log("$tag | $message")
            }
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}