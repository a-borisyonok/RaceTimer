package com.seka.racetimer.presentation.ui.adapters.timer

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    private val _viewModel: TimerViewModel = TimerViewModel(useCase)
    private var viewModel = _viewModel
    private var adapter: TimerAdapter? = TimerAdapter(context)
    private var item: Participant? = null


    private fun convertMillisToTime(time: Long) = TimeMillisConverter().convertMillisToTime(time)

    fun onBind(item: Participant) {

        this.item = item
        this.viewModel = TimerViewModel(useCase)
        this.adapter = TimerAdapter(context)

        val startOpeningTime = sharedPreferences.getLong(START_OPENING, 0)
        val startClosingTime = sharedPreferences.getLong(START_CLOSING, 0)
        val finishClosingTime = sharedPreferences.getLong(FINISH_CLOSING, 0)

        views {


            viewModel.viewModelScope.launch {
                while (true) {
                    if (startOpeningTime > System.currentTimeMillis()) {
                        startButton.isEnabled = false
                        Log.i("test disabled button", startButton.isEnabled.toString())
                    } else {
                        startButton.isEnabled = true
                        startButton.setBackgroundColor(context.getColor(R.color.start_button_color))
                        cancel()
                    }
                    delay(1000)
                }
            }

            val dnsJob = viewModel.viewModelScope.launch(Dispatchers.IO) {
                while (true) {

                    if (item.startTime == 0L && System.currentTimeMillis() >= startClosingTime) {
                        viewModel.viewModelScope.launch(Dispatchers.Main) { setVisibilityWhenDNS() }
                        viewModel.finish(item.id, 0, Long.MAX_VALUE)
                        cancel()

                    }
                    delay(1000)
                }
            }
            val dnfJob = viewModel.viewModelScope.launch(Dispatchers.IO) {
                while (true) {

                    if (item.startTime > 0 && item.finishTime == 0L && System.currentTimeMillis() >= finishClosingTime) {
                        viewModel.viewModelScope.launch(Dispatchers.Main) { setVisibilityWhenDNF() }
                        viewModel.finish(item.id, 0, Long.MAX_VALUE - 1L)
                        cancel()
                    }
                    delay(1000)
                }
            }

            startNumber.text = item.startNumber.toString()



            if (item.startTime > 0) {
                setVisibilityWhenStarted()
                participantStartTime.text = convertMillisToTime(item.startTime)
            }
            if (item.finishTime > 0) {
                setVisibilityWhenFinished()
                participantFinishTime.text = convertMillisToTime(item.finishTime)
            }

            startButton.setOnClickListener {
                dnsJob.cancel()
                val checkedStartTimeInMillis = System.currentTimeMillis()
                viewModel.start(item.id, checkedStartTimeInMillis)
                setVisibilityWhenStarted()
                participantStartTime.text = convertMillisToTime(checkedStartTimeInMillis)
            }

            finishButton.setOnClickListener {
                dnfJob.cancel()
                val checkedFinishTimeInMillis = System.currentTimeMillis()
                val raceTime = checkedFinishTimeInMillis - item.startTime
                viewModel.finish(item.id, checkedFinishTimeInMillis, raceTime)
                setVisibilityWhenFinished()
                participantFinishTime.text = convertMillisToTime(checkedFinishTimeInMillis)
            }
        }
    }


    private fun setVisibilityWhenStarted() {
        views {
            startButton.visibility = View.GONE
            participantStartTime.visibility = View.VISIBLE
            participantStartTimeHint.visibility = View.VISIBLE
            finishButton.visibility = View.VISIBLE
            dnsText.visibility = View.GONE
        }
    }

    private fun setVisibilityWhenFinished() {
        views {
            finishButton.visibility = View.GONE
            participantFinishTime.visibility = View.VISIBLE
            participantFinishTimeHint.visibility = View.VISIBLE
            dnfText.visibility = View.GONE
        }
    }

    private fun setVisibilityWhenDNS() {
        views {
            startButton.visibility = View.GONE
            dnsText.visibility = View.VISIBLE
            participantStartTime.visibility = View.GONE
            participantStartTimeHint.visibility = View.GONE
            participantFinishTimeHint.visibility = View.GONE
            participantFinishTime.visibility = View.GONE

        }
    }

    private fun setVisibilityWhenDNF() {
        views {
            finishButton.visibility = View.GONE
            dnfText.visibility = View.VISIBLE
            participantFinishTime.visibility = View.GONE
            participantFinishTimeHint.visibility = View.GONE
        }
    }


    private fun <T> views(block: PatricipantTimerBinding.() -> T): T? = binding.block()

}

