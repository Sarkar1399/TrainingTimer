package com.sarkardeveloper.trainingtimer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sarkardeveloper.trainingtimer.databinding.ActivitySetTimeBinding

class SetTimeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySetTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            intent.putExtra(
                "time",
                Time(
                    hours = hours,
                    minute = min,
                    seconds = sec
                )
            )
            setResult(Activity.RESULT_OK, intent)
            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll0 -> {
                Toast.makeText(this, "BTN 0", Toast.LENGTH_SHORT).show()
                configureTime(0)
            }
            R.id.ll1 -> {
                Toast.makeText(this, "BTN 1", Toast.LENGTH_SHORT).show()
                configureTime(1)
            }
            R.id.ll2 -> {
                Toast.makeText(this, "BTN 2", Toast.LENGTH_SHORT).show()
                configureTime(2)
            }
            R.id.ll3 -> {
                Toast.makeText(this, "BTN 3", Toast.LENGTH_SHORT).show()
                configureTime(3)
            }
            R.id.ll4 -> {
                configureTime(4)
                Toast.makeText(this, "BTN 4", Toast.LENGTH_SHORT).show()
            }
            R.id.ll5 -> {
                configureTime(5)
                Toast.makeText(this, "BTN 5", Toast.LENGTH_SHORT).show()
            }
            R.id.ll6 -> {
                configureTime(6)
                Toast.makeText(this, "BTN 6", Toast.LENGTH_SHORT).show()
            }
            R.id.ll7 -> {
                configureTime(7)
                Toast.makeText(this, "BTN 7", Toast.LENGTH_SHORT).show()
            }
            R.id.ll8 -> {
                configureTime(8)
                Toast.makeText(this, "BTN 8", Toast.LENGTH_SHORT).show()
            }
            R.id.ll9 -> {
                configureTime(9)
                Toast.makeText(this, "BTN 9", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "else", Toast.LENGTH_SHORT).show()
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
            binding.tvHours.text = "${min.toInt()}${min.take(1)}"
            binding.tvMinutes.text = "${min.drop(1)}${sec.take(1)}"
            binding.tvSeconds.text = "${sec.drop(1)}$num"
        }
    }

}