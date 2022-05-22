package com.sarkardeveloper.trainingtimer

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sarkardeveloper.trainingtimer.databinding.ActivitySetTimeBinding

class SetTimeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySetTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)

        binding.ll0.setOnClickListener(this)
        binding.ll1.setOnClickListener(this)
        binding.ll2.setOnClickListener(this)
        binding.ll3.setOnClickListener(this)
        binding.ll4.setOnClickListener(this)
        binding.ll5.setOnClickListener(this)
        binding.ll6.setOnClickListener(this)
        binding.ll7.setOnClickListener(this)
        binding.ll8.setOnClickListener(this)
        binding.ll9.setOnClickListener(this)

        binding.imgBtnSave.setOnClickListener {
            val intent = Intent()
            val sec = binding.tvSeconds.text.toString()
            val min = binding.tvMinutes.text.toString()
            val hours = binding.tvHours.text.toString()
            val timeLengthSeconds: Long =
                ((hours.toInt() * 3600) + (min.toInt() * 60) + sec.toInt()).toLong()
            sharedPreferences.edit()
                .putLong(Constants.TIME_LENGTH_SECONDS, timeLengthSeconds)
                .apply()
            intent.putExtra(
                Constants.SET_TIME,
                timeLengthSeconds
            )
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.imgBtnClear.setOnClickListener {
            clearTime()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll0 -> {
                configureTime(0)
            }
            R.id.ll1 -> {
                configureTime(1)
            }
            R.id.ll2 -> {
                configureTime(2)
            }
            R.id.ll3 -> {
                configureTime(3)
            }
            R.id.ll4 -> {
                configureTime(4)
            }
            R.id.ll5 -> {
                configureTime(5)
            }
            R.id.ll6 -> {
                configureTime(6)
            }
            R.id.ll7 -> {
                configureTime(7)
            }
            R.id.ll8 -> {
                configureTime(8)
            }
            R.id.ll9 -> {
                configureTime(9)
            }
            else -> {
                Log.d("TAG_TEST", "onClick: else")
            }
        }
    }

    private fun configureTime(num: Int) {
        val sec = binding.tvSeconds.text.toString()
        val min = binding.tvMinutes.text.toString()
        val hours = binding.tvHours.text.toString()
        if (sec.toInt() == 0) {
            binding.tvSeconds.text = "0$num"
        }
        if (sec.toInt() > 0 && min.toInt() == 0) {
            binding.tvSeconds.text = "${sec.toInt()}$num"
        }
        if (sec.toInt() > 0 && min.toInt() == 0) {
            binding.tvMinutes.text = "0${sec.take(1)}"
            binding.tvSeconds.text = "${sec.drop(1)}$num"
        }
        if (min.toInt() > 0 && sec
                .isNotEmpty() && hours.toInt() == 0
        ) {
            binding.tvMinutes.text = "${min.toInt()}${sec.take(1)}"
            binding.tvSeconds.text = "${sec.drop(1)}$num"
        }
        if (sec.isNotEmpty() && min
                .isNotEmpty() && hours.toInt() == 0
        ) {
            binding.tvHours.text = "0${min.take(1)}"
            binding.tvMinutes.text = "${min.drop(1)}${sec.take(1)}"
            binding.tvSeconds.text = "${sec.drop(1)}$num"
        }
        if (sec.isNotEmpty() && min
                .isNotEmpty() && hours.toInt() > 0
        ) {
            if (hours.take(1).toInt() <= 0) {
                binding.tvHours.text = "${hours.drop(1)}${min.dropLast(1)}"
                binding.tvMinutes.text = "${min.drop(1)}${sec.take(1)}"
                binding.tvSeconds.text = "${sec.drop(1)}$num"
            }
        }
    }

    private fun clearTime() {
        val sec = binding.tvSeconds.text.toString()
        val min = binding.tvMinutes.text.toString()
        val hours = binding.tvHours.text.toString()
        when {
            hours.toInt() > 0 && hours.toInt() % 10 > 0 -> {
                binding.tvHours.text = "0${hours.dropLast(1)}"
                binding.tvMinutes.text = "${hours.drop(1)}${min.dropLast(1)}"
                binding.tvSeconds.text = "${min.drop(1)}${sec.dropLast(1)}"
            }
            hours.toInt() > 0 && hours.toInt() % 10 == 0 -> {
                binding.tvHours.text = "00"
                binding.tvMinutes.text = "${hours.dropLast(1)}${hours.drop(1)}"
                binding.tvSeconds.text = "${min.dropLast(1)}${min.drop(1)}"
            }
            min.toInt() > 0 && min.toInt() % 10 > 0 -> {
                binding.tvMinutes.text = "0${min.dropLast(1)}"
                binding.tvSeconds.text = "${min.drop(1)}${sec.dropLast(1)}"
            }
            min.toInt() > 0 && min.toInt() % 10 == 0 -> {
                binding.tvMinutes.text = "00"
                binding.tvSeconds.text = "${min.dropLast(1)}${min.drop(1)}"
            }
            sec.toInt() > 0 && sec.toInt() % 10 > 0 -> {
                binding.tvSeconds.text = "0${sec.dropLast(1)}"
            }
            sec.toInt() > 0 && sec.toInt() % 10 == 0 -> {
                binding.tvSeconds.text = "00"
            }
            else -> {
                binding.tvHours.text = "00"
                binding.tvMinutes.text = "00"
                binding.tvSeconds.text = "00"
            }
        }
    }

}