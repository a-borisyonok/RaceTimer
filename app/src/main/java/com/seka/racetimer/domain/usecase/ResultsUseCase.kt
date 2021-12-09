package com.seka.racetimer.domain.usecase

import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class ResultsUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<List<Participant>> = repository.getResults()

}