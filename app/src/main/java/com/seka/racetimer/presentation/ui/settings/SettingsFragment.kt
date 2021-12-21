package com.seka.racetimer.presentation.ui.settings

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.seka.racetimer.R
import com.seka.racetimer.databinding.SettingsFragmentBinding
import com.seka.racetimer.other.FINISH_CLOSING
import com.seka.racetimer.other.START_CLOSING
import com.seka.racetimer.other.START_OPENING
import com.seka.racetimer.other.THEME_PREFERENCES
import com.seka.racetimer.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    private var preferenceKey: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferredTheme = sharedPreferences.getString(THEME_PREFERENCES, "")

        with(binding) {
            setTimeText()

            if (preferredTheme == "night") toggleTheme.isChecked = true
            theme.text = getString(R.string.dark_mode_text)

            toggleTheme.setOnClickListener {
                setTheme()
            }

            editOpenTime.setOnClickListener {
                preferenceKey = START_OPENING
                showTimePicker()
            }

            editCloseTime.setOnClickListener {
                preferenceKey = START_CLOSING
                showTimePicker()
            }

            editFinishCloseTime.setOnClickListener {
                preferenceKey = FINISH_CLOSING
                showTimePicker()
            }

            startTimerFromSettings.setOnClickListener {
                view.findNavController().navigate(R.id.action_settingsFragment_to_timerFragment)
            }
        }
    }

    private fun setTheme() {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {

            Configuration.UI_MODE_NIGHT_YES -> {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.toggleTheme.isActivated = true
                sharedPreferences.edit().putString(THEME_PREFERENCES, "day").apply()
            }
            Configuration.UI_MODE_NIGHT_NO -> {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.toggleTheme.isActivated = false
                sharedPreferences.edit().putString(THEME_PREFERENCES, "night").apply()
            }
        }
    }

    private fun setTimeText() {
        lifecycleScope.launchWhenStarted {
            while (true) {
                val startOpeningTime = sharedPreferences.getLong(START_OPENING, 0)
                binding.startOpenTime.text = convertMillisToTime(startOpeningTime)
                val startClosingTime = sharedPreferences.getLong(START_CLOSING, 0)
                binding.startCloseTime.text = convertMillisToTime(startClosingTime)
                val finishClosingTime = sharedPreferences.getLong(FINISH_CLOSING, 0)
                binding.finishCloseTime.text = convertMillisToTime(finishClosingTime)
                delay(500)
            }
        }
    }

    private fun convertMillisToTime(time: Long) = TimeMillisConverter().convertMillisToTime(time)

    private fun showTimePicker() {
        TimePickerDialog(sharedPreferences, preferenceKey).show(
            parentFragmentManager,
            "timePicker"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}