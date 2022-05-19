package com.sarkardeveloper.trainingtimer

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class TimerActivityContract : ActivityResultContract<Time, Time>() {
    override fun createIntent(context: Context, input: Time): Intent {
        return Intent(context, SetTimeActivity::class.java).putExtra("time", input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Time = when (resultCode) {
        Activity.RESULT_OK -> {
            intent?.getSerializableExtra("time") as Time
        }
        else -> {
            Time()
        }
    }
}