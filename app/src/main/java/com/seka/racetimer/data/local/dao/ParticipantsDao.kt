package com.seka.racetimer.data.local.dao

import androidx.room.*
import com.seka.racetimer.domain.model.Participant
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipantsDao {

    @Query("SELECT * FROM participants")
    fun getAll(): Flow<List<Participant>>

    @Query("SELECT * FROM participants ORDER BY race_time ASC")
    fun getResults(): Flow<List<Participant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(participant: Participant)

    @Query("UPDATE participants SET start_time = :startTime WHERE id = :id" )
    suspend fun setStartTime(startTime: Long, id: Int)

    @Query("UPDATE participants SET finish_time = :finishTime, race_time = :raceTime WHERE id = :id" )
    suspend fun setFinishTime(finishTime: Long, raceTime:Long, id: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(participant: Participant)

    @Delete
    suspend fun delete(participant: Participant)

    @Query("DELETE FROM participants")
    suspend fun deleteAll()
}