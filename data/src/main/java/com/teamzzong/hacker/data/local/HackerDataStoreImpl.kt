package com.teamzzong.hacker.data.local

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.teamzzong.hacker.data.BuildConfig
import com.teamzzong.hacker.domain.HackerDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val FILE_NAME = "HACKER_DATA"

class HackerDataStoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : HackerDataStore {
    private val storeDelegate by lazy {
        if (BuildConfig.DEBUG) context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        else EncryptedSharedPreferences.create(
            FILE_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override var userToken: String
        set(value) = storeDelegate.edit { putString("USER_TOKEN", value) }
        get() = storeDelegate.getString("USER_TOKEN", "") ?: ""

    override var refreshToken: String
        set(value) = storeDelegate.edit { putString("REFRESH_TOKEN", value) }
        get() = storeDelegate.getString("REFRESH_TOKEN", "") ?: ""

    override var userUUID: String
        get() = storeDelegate.getString("USER_UUID", "") ?: ""
        set(value) {
            storeDelegate.edit { putString("USER_UUID", value) }
        }

    override var social: String
        get() = storeDelegate.getString("USER_SOCIAL", "") ?: ""
        set(value) {
            storeDelegate.edit { putString("USER_SOCIAL", value) }
        }

    override var firebaseToken: String
        get() = storeDelegate.getString("FIREBASE_TOKEN", "") ?: ""
        set(value) {
            storeDelegate.edit { putString("FIREBASE_TOKEN", value) }
        }

    override var githubNickName: String
        get() = storeDelegate.getString("USER_GITHUB_NAME", "") ?: ""
        set(value) {
            storeDelegate.edit { putString("USER_GITHUB_NAME", value) }
        }

    override var userId: Int
        get() = storeDelegate.getInt("USER_ID", -1)
        set(value) {
            storeDelegate.edit { putInt("USER_ID", value) }
        }

    override var hackerNickName: String
        get() = storeDelegate.getString("USER_HACKER_NAME", "") ?: ""
        set(value) {
            storeDelegate.edit { putString("USER_HACKER_NAME", value) }
        }

    override var versionCode: Int
        get() = storeDelegate.getInt("VERSION_CODE", -1)
        set(value) {
            storeDelegate.edit { putInt("VERSION_CODE", value) }
        }

    override var isFirstAppVisit: Boolean
        get() = storeDelegate.getBoolean("IS_FIRST_APP_VISIT", false)
        set(value) {
            storeDelegate.edit { putBoolean("IS_FIRST_APP_VISIT", value) }
        }

    override fun clear() {
        storeDelegate.edit { clear() }
    }
}
