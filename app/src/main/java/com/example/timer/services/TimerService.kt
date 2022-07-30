package com.example.timer.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.timer.utils.Constants
import com.example.timer.utils.Constants.TIME_EXTRA
import java.util.*

class TimerService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null
    private val timer = Timer()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val time = intent?.getDoubleExtra(TIME_EXTRA, 0.0)
        timer.scheduleAtFixedRate(
            time?.let {
                TimeTask(it)
            },
            0, 1000
        )
        return START_NOT_STICKY
    }


    override fun onDestroy() {
        timer.cancel()
    }





    private inner class TimeTask(private var time: Double) : TimerTask() {
        override fun run() {
            val intent = Intent(Constants.TIMER_UPDATED)
            time++
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }
    }
}