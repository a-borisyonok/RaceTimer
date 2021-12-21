package com.seka.racetimer.presentation.ui.timers

import com.seka.racetimer.domain.model.Participant
import com.seka.racetimer.domain.usecase.TimerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TimerViewModelTest {
    @Mock
    lateinit var useCase: TimerUseCase
    lateinit var viewmodel: TimerViewModel


    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        viewmodel = TimerViewModel(useCase)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getParticipantsFlowTest() = runTest {
        viewmodel.getParticipants()
        Mockito.verify(useCase).getAll()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun createParticipantTest() = runTest {
        viewmodel.create(0)
        Mockito.verify(useCase).create(participant = Participant(0, 0, 0, 0))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun startTest() = runTest {
        viewmodel.start(0, 1)
        Mockito.verify(useCase).start(0, 1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun finishTest() = runTest {
        viewmodel.finish(0, 1, 1)
        Mockito.verify(useCase).finish(0, 1, 1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteAllTest() = runTest {
        viewmodel.deleteAll()
        Mockito.verify(useCase).deleteAll()
    }


    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}