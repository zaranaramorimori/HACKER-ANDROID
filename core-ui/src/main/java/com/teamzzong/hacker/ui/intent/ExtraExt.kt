package com.teamzzong.hacker.ui.intent

import android.app.Activity
import android.os.Parcelable
import kotlin.properties.ReadOnlyProperty

fun intExtra(defaultValue: Int = -1) = ReadOnlyProperty<Activity, Int> { thisRef, property ->
    thisRef.intent.extras?.getInt(
        property.name,
        defaultValue
    ) ?: defaultValue
}

fun longExtra(defaultValue: Long = -1) = ReadOnlyProperty<Activity, Long> { thisRef, property ->
    thisRef.intent.extras?.getLong(
        property.name,
        defaultValue
    ) ?: defaultValue
}

fun boolExtra(defaultValue: Boolean = false) =
    ReadOnlyProperty<Activity, Boolean> { thisRef, property ->
        thisRef.intent.extras?.getBoolean(
            property.name,
            defaultValue
        ) ?: defaultValue
    }

fun stringExtra(defaultValue: String? = null) =
    ReadOnlyProperty<Activity, String?> { thisRef, property ->
        if (defaultValue == null) thisRef.intent.extras?.getString(property.name)
        else thisRef.intent.extras?.getString(property.name, defaultValue)
    }

fun <P : Parcelable> parcelableExtra(defaultValue: P? = null) =
    ReadOnlyProperty<Activity, P?> { thisRef, property ->
        thisRef.intent.extras?.getParcelable<P>(property.name) ?: defaultValue
    }
