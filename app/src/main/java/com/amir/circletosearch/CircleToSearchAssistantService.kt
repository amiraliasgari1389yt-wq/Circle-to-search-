package com.amir.circletosearch

import android.content.Intent
import android.content.SharedPreferences
import android.service.voice.VoiceInteractionService

class CircleToSearchAssistantService : VoiceInteractionService() {

    private val prefsName = "circle_to_search"
    private val targetPackageKey = "target_package"

    override fun onReady() {
        super.onReady()
        launchTarget()
    }

    private fun launchTarget() {
        val prefs: SharedPreferences = getSharedPreferences(prefsName, MODE_PRIVATE)
        val packageName = prefs.getString(targetPackageKey, null) ?: return

        val launchIntent = packageManager.getLaunchIntentForPackage(packageName) ?: return

        launchIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(launchIntent)
    }
}
