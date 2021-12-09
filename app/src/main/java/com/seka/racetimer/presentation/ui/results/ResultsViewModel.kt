package com.seka.racetimer.presentation.ui.results

import androidx.lifecycle.ViewModel
import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.domain.usecase.ResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(private val useCase: ResultsUseCase) : ViewModel() {


    fun getResults(): Flow<List<Participant>> = useCase.invoke()

}

