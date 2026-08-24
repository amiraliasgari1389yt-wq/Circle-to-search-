package com.amir.circletosearch

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionService
import android.speech.SpeechRecognizer

class CircleToSearchRecognitionService : RecognitionService() {

    override fun onStartListening(
        recognizerIntent: Intent?,
        listener: Callback?
    ) {
        listener?.error(SpeechRecognizer.ERROR_RECOGNIZER_BUSY)
    }

    override fun onCancel(listener: Callback?) {
    }

    override fun onStopListening(listener: Callback?) {
    }
}
