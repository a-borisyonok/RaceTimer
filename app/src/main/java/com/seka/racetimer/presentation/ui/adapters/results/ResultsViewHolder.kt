package com.seka.racetimer.presentation.ui.adapters.results

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.seka.racetimer.R
import com.seka.racetimer.databinding.ParticipantResultBinding
import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.util.TimeMillisConverter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ResultsViewHolder @Inject constructor(
    private val binding: ParticipantResultBinding,
    @ApplicationContext private val context: Context

) : RecyclerView.ViewHolder(binding.root) {


    private var adapter: ResultsAdapter? = ResultsAdapter(context)
    private var item: Participant? = null


    fun onBind(item: Participant) {

        this.item = item
        this.adapter = ResultsAdapter(context)

        with(binding) {
            startNumber.text = item.startNumber.toString()
            when {
                item.startTime == 0L -> setVisibilityWhenDNS()
                item.finishTime == 0L -> setVisibilityWhenDNF(item)
                else -> setVisibilityWhenOK(item)
            }
        }
    }

    private fun setVisibilityWhenDNS() {

        with(binding) {
            resultStartTime.text = context.getString(R.string.dns_text)
            resultFinishTime.text = ""
            resultDnsText.visibility = View.VISIBLE

        }
    }

    private fun setVisibilityWhenDNF(item: Participant) {

        with(binding){
            resultStartTime.text = convertMillisToTime(item.startTime)
            resultFinishTime.text = context.getString(R.string.dnf_text)
            resultDnfText.visibility = View.VISIBLE
        }
    }

    private fun setVisibilityWhenOK(item: Participant) {

        with(binding) {
            position.visibility = View.VISIBLE
            raceTime.visibility = View.VISIBLE
            raceTimeText.visibility = View.VISIBLE

            resultStartTime.text = convertMillisToTime(item.startTime)
            resultFinishTime.text = convertMillisToTime(item.finishTime)
            raceTime.text = convertRaceDurationToTime(item.raceTime)

            position.visibility = View.VISIBLE
            position.text = (adapterPosition + 1).toString()
        }
    }

    private fun convertMillisToTime(millis: Long) = TimeMillisConverter().convertMillisToTime(millis)

    private fun convertRaceDurationToTime(duration: Long): String =
        TimeMillisConverter().convertRaceDurationToTime(duration)



}

