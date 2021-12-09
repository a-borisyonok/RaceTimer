package com.seka.racetimer.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "participants")
data class Participant(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id")val id: Int = 0,
    @ColumnInfo (name = "start_number")val startNumber: Int,
    @ColumnInfo (name = "start_time")val startTime: Long = 0L,
    @ColumnInfo (name = "finish_time")val finishTime: Long = 0L,
    @ColumnInfo (name = "race_time")val raceTime: Long = Long.MAX_VALUE-2
)