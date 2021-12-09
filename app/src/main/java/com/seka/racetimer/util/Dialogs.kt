package com.seka.racetimer.util

import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.seka.racetimer.R
import com.seka.racetimer.presentation.ui.timers.TimerFragment
import com.seka.racetimer.presentation.ui.timers.TimerViewModel

class Dialogs {
    fun showEnterStartNumberDialog(context: Context, viewModel: TimerViewModel) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Start number")

        val input = EditText(context)

        input.hint = "Enter start number"
        input.inputType = InputType.TYPE_CLASS_NUMBER

        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->

            val number = input.text.toString().toLongOrNull()
            if (number != null && number > 0 && number.toString().length <= 4) {
                viewModel.create(number.toInt())
            } else {
                Toast.makeText(context, "Wrong Number!", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    fun showDeleteAllDialog(context: Context, viewModel: TimerViewModel, view: View) {
        AlertDialog.Builder(context)
            .setTitle("Delete all?")

            .setPositiveButton("OK") { _, _ ->
                viewModel.deleteAll()
                view.findNavController().navigate(R.id.action_timerFragment_to_settingsFragment)
                          }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }
}