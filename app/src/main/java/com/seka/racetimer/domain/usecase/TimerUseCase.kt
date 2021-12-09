package com.seka.racetimer.domain.usecase

import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimerUseCase @Inject constructor(
    private val repository: Repository
) {


    suspend fun create(participant: Participant) = repository.create(participant)
    fun getAll(): Flow<List<Participant>> = repository.getAll()
    suspend fun start(id: Int, startTime: Long) = repository.start(startTime, id)
    suspend fun finish(id: Int, finishTime: Long, raceTime: Long) =
        repository.finish(finishTime, raceTime, id)

    suspend fun deleteAll() = repository.deleteAll()
}