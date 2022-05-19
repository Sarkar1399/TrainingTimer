package com.sarkardeveloper.trainingtimer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sarkardeveloper.trainingtimer.Constants.CHANNEL_ID
import com.sarkardeveloper.trainingtimer.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var secondsRemaining: Long = 0
    private var isStart: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgCart.setOnClickListener {
            activityLauncher.launch(Time())
        }
        binding.tvTimer.setOnClickListener {
            if (!isStart) {
                isStart = true
                startTimer()
            } else {
                isStart = false
                onPauseTimer()
            }
        }
        binding.progressBar.max = 0
        binding.progressBar.progress = 0

    }

    private val activityLauncher = registerForActivityResult(TimerActivityContract()) { result ->
        val sb = StringBuilder("")
        sb.append("${if (result.hours.toInt() > 0) "${result.hours} : " else ""}")
        sb.append("${if (result.minute.toInt() > 0) "${result.minute} : " else "0 : "}")
        sb.append("${if (result.seconds.toInt() > 0) "${if (result.seconds.toString().length == 2) "${result.seconds}" else "0${result.seconds}"}" else ""}")
        binding.tvTimer.text = sb.toString()

        timerLengthSeconds =
            (result.hours.toInt() * 3600 + result.minute.toInt() * 60 + result.seconds.toInt()).toLong()
        binding.progressBar.max =
            result.hours.toInt() * 3600 + result.minute.toInt() * 60 + result.seconds.toInt()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timerLengthSeconds * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                Log.d("TAG_TEST", "onTick: $secondsRemaining")
                updateCountdownUI()
            }
        }.start()
    }

    private fun onPauseTimer() {
        timer.cancel()
    }

    private fun onTimerFinished() {
        updateCountdownUI()
        startNotification()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        binding.tvTimer.text =
            "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0$secondsStr"}"
        binding.progressBar.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun startNotification() {
        createNotificationChannel()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_cart)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...")
            ).setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
        if (Build.VERSION.SDK_INT >= 26) {
            (getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            (getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(5000)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}