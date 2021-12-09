package com.seka.racetimer.util

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*
import javax.inject.Inject


class TimePickerDialog @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val preferenceKey: String
) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, 1, this, hour, minute, !is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val checkedTimeAtCurrentDate = LocalDate.now()
            .atStartOfDay()
            .plusHours(hourOfDay.toLong())
            .plusMinutes(minute.toLong())
        val millis = checkedTimeAtCurrentDate.toInstant(ZoneOffset.ofHours(3)).toEpochMilli()
        sharedPreferences.edit().putLong(preferenceKey, millis).apply()


    }
}

