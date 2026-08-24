package com.amir.circletosearch

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService

class CircleToSearchSessionService : VoiceInteractionSessionService() {

    override fun onNewSession(args: Bundle?): VoiceInteractionSession {
        return CircleToSearchSession(this)
    }
}

class CircleToSearchSession(
    private val service: VoiceInteractionSessionService
) : VoiceInteractionSession(service) {

    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)
        launchTarget()
    }

    private fun launchTarget() {
        val prefs: SharedPreferences =
            service.getSharedPreferences("circle_to_search", 0)

        val packageName =
            prefs.getString("target_package", null) ?: return

        val launchIntent =
            service.packageManager.getLaunchIntentForPackage(packageName)
                ?: return

        launchIntent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        )

        service.startActivity(launchIntent)
        finish()
    }
}
