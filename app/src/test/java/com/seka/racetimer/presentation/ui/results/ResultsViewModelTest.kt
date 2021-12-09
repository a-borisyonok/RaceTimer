package com.seka.racetimer.presentation.ui.results

import com.seka.racetimer.domain.usecase.ResultsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResultsViewModelTest {
@Mock
lateinit var useCase: ResultsUseCase
lateinit var viewmodel: ResultsViewModel


@ExperimentalCoroutinesApi
@Before
fun setup(){
    viewmodel = ResultsViewModel(useCase)
    Dispatchers.setMain(UnconfinedTestDispatcher())
}

    @ExperimentalCoroutinesApi
    @Test
    fun getResultsFlowTest() = runTest {
        viewmodel.getResults()
        verify(useCase).invoke()
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

}