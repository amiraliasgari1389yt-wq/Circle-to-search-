package com.amir.circletosearch

import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService

class CircleToSearchSessionService : VoiceInteractionSessionService() {

    override fun onNewSession(args: Bundle?): VoiceInteractionSession {
        return CircleToSearchSession(this)
    }
}

class CircleToSearchSession(
    private val sessionService: VoiceInteractionSessionService
) : VoiceInteractionSession(sessionService) {

    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)

        val prefs = sessionService.getSharedPreferences(
            "circle_to_search",
            MODE_PRIVATE
        )

        val packageName = prefs.getString(
            "target_package",
            null
        )

        if (packageName != null) {
            val launchIntent =
                sessionService.packageManager.getLaunchIntentForPackage(packageName)

            if (launchIntent != null) {
                launchIntent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                )

                sessionService.startActivity(launchIntent)
            }
        }

        hide()
    }
}
