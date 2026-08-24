package com.amir.circletosearch

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
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

        try {
            val cameraManager =
                sessionService.getSystemService(Context.CAMERA_SERVICE)
                        as CameraManager

            val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
                val characteristics =
                    cameraManager.getCameraCharacteristics(id)

                characteristics.get(
                    CameraCharacteristics.FLASH_INFO_AVAILABLE
                ) == true
            }

            if (cameraId != null) {
                cameraManager.setTorchMode(cameraId, true)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        hide()
    }
}
