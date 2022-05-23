package com.sarkardeveloper.trainingtimer

import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sarkardeveloper.trainingtimer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var secondsRemainingTime: Long = 0
    private var isStart: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences

    private var mAudioManager: AudioManager? = null
    private var mRemoteControlResponder: ComponentName? = null
    private val remoteControlReceiver = RemoteControlReceiver()

    companion object {
        var ins: MainActivity? = null
        fun getInstance(): MainActivity? {
            return ins
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ins = this
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)

        mAudioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        mRemoteControlResponder = ComponentName(
            packageName,
            remoteControlReceiver::class.java.name
        )


        binding.imgCart.setOnClickListener {
            if (secondsRemainingTime != 0L) {
                timer.cancel()
                secondsRemainingTime = 0
            }
            activityLauncher.launch(
                sharedPreferences.getLong(Constants.TIME_LENGTH_SECONDS, 0)
            )
        }
        binding.tvTimer.setOnClickListener {
            if (!isStart) {
                isStart = true
                if (secondsRemainingTime != 0L) {
                    binding.progressBar.max = timerLengthSeconds.toInt()
                    binding.progressBar.progress = secondsRemainingTime.toInt()
                    startTimer(timerLengthSeconds - secondsRemainingTime)
                } else {
                    binding.progressBar.max = timerLengthSeconds.toInt()
                    binding.progressBar.progress = secondsRemainingTime.toInt()
                    startTimer(timerLengthSeconds)
                }
            } else {
                isStart = false
                onPauseTimer()
            }
        }
        timerLengthSeconds =
            sharedPreferences.getLong(Constants.TIME_LENGTH_SECONDS, 0)
        binding.tvTimer.text = configureTime(timerLengthSeconds)
        binding.progressBar.max = timerLengthSeconds.toInt()
        binding.progressBar.progress = 0
    }

    fun updateTheTextView() {
        this@MainActivity.runOnUiThread {
            startTimer(timerLengthSeconds)
        }
    }

    private val activityLauncher = registerForActivityResult(TimerActivityContract()) { result ->
        binding.tvTimer.text = configureTime(result)
        timerLengthSeconds = result
        binding.progressBar.max = timerLengthSeconds.toInt()
        binding.progressBar.progress = 0
    }

    private fun startTimer(secondsRemainingTime: Long) {
        Log.d("TAG_TEST", "startTimer: $secondsRemainingTime")
        timer = object : CountDownTimer(secondsRemainingTime * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                Log.d("TAG_TEST", "onTick: $millisUntilFinished")
                updateCountdownUI(millisUntilFinished / 1000)
            }
        }.start()
    }

    private fun onPauseTimer() {
        isStart = false
        sharedPreferences.edit()
            .putLong(Constants.SECONDS_REMAINING_TIME, secondsRemainingTime)
            .apply()
        timer.cancel()
    }

    private fun onTimerFinished() {
        isStart = false
        secondsRemainingTime = 0
        NotificationUtils.startNotification()
    }

    private fun configureTime(timeLengthSeconds: Long): String {
        val sb = StringBuilder("")
        val hours = timeLengthSeconds / 3600
        val minute = (timeLengthSeconds - (hours * 3600)) / 60
        val seconds = timeLengthSeconds - ((hours * 3600) + (minute * 60))
        sb.append("${if (hours > 0) "$hours : " else ""}")
        sb.append("${if (minute > 0) "$minute : " else "0 : "}")
        sb.append("${if (seconds > 0) "${if (seconds.toString().length == 2) "$seconds" else "0${seconds}"}" else "00"}")
        return sb.toString()
    }

    private fun updateCountdownUI(secondsRemaining: Long = 0) {
        binding.tvTimer.text = configureTime(secondsRemaining)
        secondsRemainingTime = timerLengthSeconds - secondsRemaining
        binding.progressBar.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    override fun onResume() {
        super.onResume()
        mAudioManager?.registerMediaButtonEventReceiver(
            mRemoteControlResponder
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mAudioManager?.unregisterMediaButtonEventReceiver(
            mRemoteControlResponder
        )
    }

}