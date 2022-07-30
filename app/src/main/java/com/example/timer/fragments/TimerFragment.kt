package com.example.timer.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.timer.R
import com.example.timer.databinding.FragmentTimerBinding
import com.example.timer.services.TimerService
import com.example.timer.utils.Constants.TIMER_UPDATED
import com.example.timer.utils.Constants.TIME_EXTRA
import kotlin.math.roundToInt

class TimerFragment : Fragment(R.layout.fragment_timer) {


    private lateinit var binding: FragmentTimerBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)

        binding.startPauseBtn.setOnClickListener { startPauseTimer() }
        binding.resetBtn.setOnClickListener { resetTimer() }

        serviceIntent = Intent(activity?.applicationContext, TimerService::class.java)
        activity?.registerReceiver(updateTime, IntentFilter(TIMER_UPDATED))
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TIME_EXTRA, 0.0)
            binding.mainFragmentTimerTv.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Int, minutes: Int, seconds: Int): String =
        String.format("%02d:%02d:%02d", hours, minutes, seconds)


    private fun startPauseTimer() {
        if (timerStarted) pauseTimer() else startTimer()
    }

    private fun startTimer() {
        serviceIntent.putExtra(TIME_EXTRA, time)
        activity?.startService(serviceIntent)
        binding.startPauseBtn.text = getString(R.string.pause)
        binding.startPauseBtn.icon = activity?.getDrawable(R.drawable.pause)
        timerStarted = true
    }

    private fun pauseTimer() {
        activity?.stopService(serviceIntent)
        binding.startPauseBtn.text = getString(R.string.start)
        binding.startPauseBtn.icon = activity?.getDrawable(R.drawable.start)
        timerStarted = false
    }

    private fun resetTimer() {
        pauseTimer()
        time = 0.0
        binding.mainFragmentTimerTv.text=getTimeStringFromDouble(time)
    }
}