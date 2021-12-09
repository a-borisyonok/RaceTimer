package com.seka.racetimer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seka.racetimer.data.local.dao.ParticipantsDao
import com.seka.racetimer.domain.model.Participant


@Database(entities = [Participant::class], version = 2, exportSchema = false)
abstract class ParticipantsDB : RoomDatabase() {

    abstract fun participantsDao(): ParticipantsDao
}
