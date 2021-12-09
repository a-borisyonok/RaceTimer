package com.seka.racetimer.domain.repository

import com.seka.racetimer.data.local.dao.ParticipantsDao
import com.seka.racetimer.domain.model.Participant
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class Repository @Inject constructor(
    private val dao: ParticipantsDao,

    ) {


    fun getAll(): Flow<List<Participant>> = dao.getAll()
    fun getResults(): Flow<List<Participant>> = dao.getResults()
    suspend fun create(participant: Participant) = dao.add(participant)
    suspend fun start(startTime: Long, id: Int) = dao.setStartTime(startTime, id)
    suspend fun finish(finishTime: Long, raceTime: Long, id: Int) =
        dao.setFinishTime(finishTime, raceTime, id)

    suspend fun deleteAll() = dao.deleteAll()


}