package com.seka.racetimer.presentation.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.seka.racetimer.domain.model.Participant


class ParticipantDiffCallback  : DiffUtil.ItemCallback<Participant>() {
    override fun areItemsTheSame(oldItem: Participant, newItem: Participant): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Participant, newItem: Participant): Boolean {
       return oldItem == newItem

    }


}