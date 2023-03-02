package com.teamzzong.hacker.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import com.teamzzong.hacker.data.remote.UserService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileWidgetReceiver : GlanceAppWidgetReceiver() {
    @Inject
    lateinit var userService: UserService

    override val glanceAppWidget: GlanceAppWidget = ProfileWidget()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        GlobalScope.launch {
            subscribeData(context)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        GlobalScope.launch {
            subscribeData(context)
        }
    }

    private suspend fun subscribeData(context: Context) {
        val glanceId =
            GlanceAppWidgetManager(context).getGlanceIds(ProfileWidget::class.java).firstOrNull()
        glanceId?.let {
            updateAppWidgetState(context, it) { state ->
                runCatching {
                    userService.fetchMyDetailProfile()
                }.onSuccess { response ->
                    val result = response.data.toEntity()
                    state[NameKey] = result.user.nickname
                    state[ProfileImageKey] = result.head
                    state[CommitCountKey] = result.user.hairCount.toString()
                    glanceAppWidget.update(context, it)
                }
            }
        }
    }

    companion object {
        val NameKey = stringPreferencesKey("profile_name")
        val ProfileImageKey = stringPreferencesKey("profile_image_url")
        val CommitCountKey = stringPreferencesKey("profile_commit_count")
    }
}
