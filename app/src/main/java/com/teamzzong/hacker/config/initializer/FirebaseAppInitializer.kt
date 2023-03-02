package com.teamzzong.hacker.config.initializer

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.FirebaseApp

class FirebaseAppInitializer : Initializer<FirebaseApp> {
    override fun create(context: Context): FirebaseApp {
        return FirebaseApp.initializeApp(context) ?: FirebaseApp.getInstance()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
