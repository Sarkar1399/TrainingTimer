package com.sarkardeveloper.trainingtimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.view.KeyEvent

class RemoteControlReceiver : BroadcastReceiver() {

    private var actionMediaButtonListener: ActionMediaButtonListener? = null
    fun setMediaButtonListener(listener: ActionMediaButtonListener) {
        this.actionMediaButtonListener = listener
        Log.d("TAG_TEST", "setMediaButtonListener: $listener")
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_MEDIA_BUTTON == intent?.action) {
            if (!TrainingTimerApp.isActionMediaButton) {
                val mediaPlayer: MediaPlayer =
                    MediaPlayer.create(TrainingTimerApp.appContext, R.raw.hello_hi)
                mediaPlayer.start()
                actionMediaButtonListener?.startListener("onReceiver")
                Log.d("TAG_TEST", "onReceive: tetete")
                TrainingTimerApp.isActionMediaButton = true
            } else {
                TrainingTimerApp.isActionMediaButton = false
            }
        }
    }
}