package com.seka.racetimer.presentation.ui.adapters.results

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.seka.racetimer.databinding.ParticipantResultBinding
import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.presentation.ui.adapters.ParticipantDiffCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResultsAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    ListAdapter<Participant, ResultsViewHolder>(ParticipantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(
            ParticipantResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
       return  holder.onBind(getItem(position))
    }

}
