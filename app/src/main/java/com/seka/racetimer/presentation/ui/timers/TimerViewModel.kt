package com.seka.racetimer.presentation.ui.timers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.domain.usecase.TimerUseCase
import com.seka.racetimer.util.TimeMillisConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(private val useCase: TimerUseCase) : ViewModel() {

    fun getParticipants(): Flow<List<Participant>> = useCase.getAll()

    fun create(number: Int) {
        viewModelScope.launch { useCase.create(createParticipant(number)) }
    }

    fun start(id: Int, startTime: Long) {
        viewModelScope.launch { useCase.start(id, startTime) }
    }

    fun finish(id: Int, finishTime: Long, raceTime: Long) {
        viewModelScope.launch { useCase.finish(id, finishTime, raceTime) }
    }

    fun deleteAll() {
        viewModelScope.launch { useCase.deleteAll() }
    }

    private fun createParticipant(number: Int) = Participant(startNumber = number)

}


