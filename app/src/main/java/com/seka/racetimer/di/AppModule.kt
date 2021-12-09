package com.seka.racetimer.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.seka.racetimer.domain.repository.Repository
import com.seka.racetimer.data.local.ParticipantsDB
import com.seka.racetimer.data.local.dao.ParticipantsDao
import com.seka.racetimer.domain.usecase.ResultsUseCase
import com.seka.racetimer.domain.usecase.TimerUseCase
import com.seka.racetimer.presentation.ui.adapters.timer.TimerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ParticipantsDB {
        return Room.databaseBuilder(
            appContext,
            ParticipantsDB::class.java,
            "race.db"
        ).build()
    }

    @Provides
    fun provideParticipantsDAO(database: ParticipantsDB): ParticipantsDao =
        database.participantsDao()
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
             dao: ParticipantsDao
    ): Repository {

        return Repository(dao)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun provideTimerAdapter(@ApplicationContext context: Context) = TimerAdapter(context)
}

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("raceSettings", Context.MODE_PRIVATE)
}


