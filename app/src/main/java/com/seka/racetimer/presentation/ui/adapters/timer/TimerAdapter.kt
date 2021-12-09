package com.seka.racetimer.presentation.ui.adapters.timer

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.autofill.AutofillId
import androidx.recyclerview.widget.ListAdapter
import com.seka.racetimer.databinding.PatricipantTimerBinding
import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.presentation.ui.adapters.ParticipantDiffCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TimerAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    ListAdapter<Participant, TimerViewHolder>(ParticipantDiffCallback()) {
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {

        return TimerViewHolder(
            PatricipantTimerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }


    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {

        holder.onBind(getItem(position))
    }


}
