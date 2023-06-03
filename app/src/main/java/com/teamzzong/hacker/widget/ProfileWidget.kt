package com.teamzzong.hacker.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.*
import androidx.glance.action.Action
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import com.teamzzong.hacker.presentation.HomeActivity
import com.teamzzong.hacker.widget.ProfileWidgetReceiver.Companion.CommitCountKey
import com.teamzzong.hacker.widget.ProfileWidgetReceiver.Companion.NameKey
import com.teamzzong.hacker.widget.ProfileWidgetReceiver.Companion.ProfileImageKey

class ProfileWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val name = currentState(NameKey)
        val profileUrl = currentState(ProfileImageKey)
        val commitCount = currentState(CommitCountKey)
        Column(
            modifier = GlanceModifier.fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                provider = ImageProvider(com.teamzzong.hacker.R.drawable.ic_launcher_foreground),
                contentDescription = "Image"
            )
            Text(text = name ?: "")
            Text(text = "${commitCount ?: "null"} 커밋")
        }
    }

    private fun actionLaunchActivity(context: Context): Action =
        actionStartActivity(HomeActivity.getWidgetIntent(context))
}