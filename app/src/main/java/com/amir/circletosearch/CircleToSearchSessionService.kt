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
    service: VoiceInteractionSessionService
) : VoiceInteractionSession(service) {

    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)

        val prefs = getSharedPreferences("circle_to_search", 0)
        val packageName = prefs.getString("target_package", null)

        if (packageName != null) {
            val intent = packageManager.getLaunchIntentForPackage(packageName)

            if (intent != null) {
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                )
                startActivity(intent)
            }
        }

        hide()
    }
}
