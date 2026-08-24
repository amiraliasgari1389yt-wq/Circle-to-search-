package com.amir.circletosearch

import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService

class CircleToSearchSessionService : VoiceInteractionSessionService() {

    override fun onNewSession(args: android.os.Bundle?): VoiceInteractionSession {
        return VoiceInteractionSession(this)
    }
}
