package com.sarkardeveloper.trainingtimer

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class TimerActivityContract : ActivityResultContract<Long, Long>() {
    override fun createIntent(context: Context, input: Long): Intent {
        return Intent(context, SetTimeActivity::class.java).putExtra(Constants.SET_TIME, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Long = when (resultCode) {
        Activity.RESULT_OK -> {
            intent?.getLongExtra(Constants.SET_TIME, 0) as Long
        }
        else -> {
            0L
        }
    }
}