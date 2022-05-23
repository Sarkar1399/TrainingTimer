package com.sarkardeveloper.trainingtimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.view.KeyEvent

class RemoteControlReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_MEDIA_BUTTON == intent?.action) {
            if (!TrainingTimerApp.isActionMediaButton) {
                try {
                    MainActivity.getInstance()?.updateTheTextView()
                } catch (e: Exception) {
                    Log.d("TAG_TEST", "onReceive: ${e.message}")
                }
                TrainingTimerApp.isActionMediaButton = true
            } else {
                TrainingTimerApp.isActionMediaButton = false
            }
        }
    }
}