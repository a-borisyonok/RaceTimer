package com.seka.racetimer.presentation.ui.start

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.seka.racetimer.R

import com.seka.racetimer.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StartFragment : Fragment() {

private var binding: FragmentStartBinding? = null

@Inject
lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View= FragmentStartBinding.inflate(inflater).also { binding = it }.root

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
    private fun <T> views(block: FragmentStartBinding.() -> T): T? = binding?.block()
    }

