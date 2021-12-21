package com.seka.racetimer.presentation.ui.adapters.timer

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.seka.racetimer.R
import com.seka.racetimer.databinding.PatricipantTimerBinding
import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.domain.usecase.TimerUseCase
import com.seka.racetimer.other.FINISH_CLOSING
import com.seka.racetimer.other.START_CLOSING
import com.seka.racetimer.other.START_OPENING
import com.seka.racetimer.presentation.ui.timers.TimerViewModel
import com.seka.racetimer.util.TimeMillisConverter
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import javax.inject.Inject


class TimerViewHolder @Inject constructor(
    private val binding: PatricipantTimerBinding,
    @ApplicationContext private val context: Context,

    ) : RecyclerView.ViewHolder(binding.root) {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface Injection {
        fun getUseCase(): TimerUseCase
        fun getSharedPreferences(): SharedPreferences

    }


    private val useCase: TimerUseCase =
        EntryPointAccessors.fromApplication(context, Injection::class.java)
            .getUseCase()


    private val sharedPreferences: SharedPreferences =
        EntryPointAccessors.fromApplication(context, Injection::class.java).getSharedPreferences()


    private var viewModel: TimerViewModel = TimerViewModel(useCase)
    private var adapter: TimerAdapter? = TimerAdapter(context)
    private var item: Participant? = null


    private fun convertMillisToTime(time: Long) = TimeMillisConverter().convertMillisToTime(time)

    fun onBind(item: Participant) {

        this.item = item
        this.viewModel = TimerViewModel(useCase)
        this.adapter = TimerAdapter(context)

        setStartButtonVisibility()

        val dnsJob = setVisibilityIfDNS(item)
        val dnfJob = setVisibilityIfDNF(item)

        with(binding) {

            startNumber.text = item.startNumber.toString()

            if (item.startTime > 0) {
                dnsJob.cancel()
                setVisibilityWhenStarted()
                participantStartTime.text = convertMillisToTime(item.startTime)
            }
            if (item.finishTime > 0) {
                dnfJob.cancel()
                setVisibilityWhenFinished()
                participantFinishTime.text = convertMillisToTime(item.finishTime)
            }

            startButton.setOnClickListener {
                dnsJob.cancel()
                checkStart(item)
            }

            finishButton.setOnClickListener {
                dnfJob.cancel()
                checkFinish(item)
            }
        }
    }

    private fun setStartButtonVisibility() {
        val startOpeningTime = sharedPreferences.getLong(START_OPENING, 0)
        with(binding) {
            viewModel.viewModelScope.launch {
                while (true) {
                    if (startOpeningTime > System.currentTimeMillis()) {
                        startButton.isEnabled = false

                    } else {
                        startButton.isEnabled = true
                        startButton.setBackgroundColor(context.getColor(R.color.start_button_color))
                        cancel()
                    }
                    delay(1000)
                }
            }
        }
    }

    private fun checkStart(item: Participant) {

        val checkedStartTimeInMillis = System.currentTimeMillis()
        viewModel.start(item.id, checkedStartTimeInMillis)
        setVisibilityWhenStarted()
        binding.participantStartTime.text = convertMillisToTime(checkedStartTimeInMillis)
    }

    private fun setVisibilityWhenStarted() {

        with(binding) {
            startButton.visibility = View.GONE
            participantStartTime.visibility = View.VISIBLE
            participantStartTimeHint.visibility = View.VISIBLE
            finishButton.visibility = View.VISIBLE
            dnsText.visibility = View.GONE
        }
    }

    private fun checkFinish(item: Participant) {

        val checkedFinishTimeInMillis = System.currentTimeMillis()
        val raceTime = checkedFinishTimeInMillis - item.startTime
        viewModel.finish(item.id, checkedFinishTimeInMillis, raceTime)
        binding.participantFinishTime.text = convertMillisToTime(checkedFinishTimeInMillis)
        setVisibilityWhenFinished()
    }

    private fun setVisibilityWhenFinished() {

        with(binding) {
            finishButton.visibility = View.GONE
            participantFinishTime.visibility = View.VISIBLE
            participantFinishTimeHint.visibility = View.VISIBLE
            dnfText.visibility = View.GONE
        }
    }

    private fun setVisibilityIfDNS(item: Participant): Job {

        val startClosingTime = sharedPreferences.getLong(START_CLOSING, 0)

        return with(binding) {

            viewModel.viewModelScope.launch(Dispatchers.IO) {
                while (true) {

                    if (item.startTime == 0L && System.currentTimeMillis() >= startClosingTime) {

                        viewModel.viewModelScope.launch(Dispatchers.Main) {

                            startButton.visibility = View.GONE
                            dnsText.visibility = View.VISIBLE
                            participantStartTime.visibility = View.GONE
                            participantStartTimeHint.visibility = View.GONE
                            participantFinishTimeHint.visibility = View.GONE
                            participantFinishTime.visibility = View.GONE
                            finishButton.visibility = View.GONE
                        }

                        viewModel.finish(item.id, 0, Long.MAX_VALUE)
                        cancel()

                    }
                    delay(1000)
                }
            }
        }
    }

    private fun setVisibilityIfDNF(item: Participant): Job {

        val finishClosingTime = sharedPreferences.getLong(FINISH_CLOSING, 0)

        return with(binding) {

            viewModel.viewModelScope.launch(Dispatchers.IO) {

                while (true) {

                    if (item.startTime > 0 && item.finishTime == 0L && System.currentTimeMillis() >= finishClosingTime) {

                        viewModel.viewModelScope.launch(Dispatchers.Main) {

                            finishButton.visibility = View.GONE
                            dnfText.visibility = View.VISIBLE
                            participantFinishTime.visibility = View.GONE
                            participantFinishTimeHint.visibility = View.GONE
                        }
                        viewModel.finish(item.id, 0, Long.MAX_VALUE - 1L)
                        cancel()
                    }
                    delay(1000)
                }
            }
        }
    }
}

