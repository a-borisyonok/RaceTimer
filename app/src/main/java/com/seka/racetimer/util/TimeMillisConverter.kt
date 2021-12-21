package com.seka.racetimer.util

import java.text.SimpleDateFormat
import java.util.*

class TimeMillisConverter {

     fun convertMillisToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

     fun convertRaceDurationToTime(duration: Long): String {

        val seconds = ((duration / 1000) % 60)
        val minutes = ((duration / (1000 * 60)) % 60)
        val hours = ((duration / (1000 * 60 * 60)) % 24)

        val hoursToString = if (hours < 10) "0$hours" else "$hours"
        val minutesToString = if (minutes < 10) "0$minutes" else "$minutes"
        val secondsToString = if (seconds < 10) "0$seconds" else "$seconds"

        return "$hoursToString:$minutesToString:$secondsToString"
    }

}