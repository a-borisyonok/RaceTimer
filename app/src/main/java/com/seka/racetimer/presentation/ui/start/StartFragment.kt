package com.seka.racetimer.presentation.ui.start

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.seka.racetimer.R
import com.seka.racetimer.databinding.FragmentStartBinding
import com.seka.racetimer.other.THEME_PREFERENCES
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUITheme()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentStartBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views {

            prefsButton.setOnClickListener {
                view.findNavController().navigate(R.id.action_startFragment_to_settingsFragment)
            }
            startTimerButton.setOnClickListener {
                view.findNavController().navigate(R.id.action_startFragment_to_timerFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setUITheme() {

        val preferredTheme = sharedPreferences.getString(THEME_PREFERENCES, "")

        when {

            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
                    Configuration.UI_MODE_NIGHT_YES && preferredTheme.isNullOrEmpty() -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putString(THEME_PREFERENCES, "night").apply()
            }

            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
                    Configuration.UI_MODE_NIGHT_NO && preferredTheme.isNullOrEmpty() ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            preferredTheme == "night" ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            preferredTheme == "day" ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun <T> views(block: FragmentStartBinding.() -> T): T? = binding?.block()
}


