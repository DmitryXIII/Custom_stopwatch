package com.ineedyourcode.customstopwatch.data

import com.ineedyourcode.customstopwatch.domain.StopwatchNumber
import com.ineedyourcode.customstopwatch.domain.usecase.StopwatchOrchestratorUsecase
import com.ineedyourcode.customstopwatch.domain.StopwatchStateHolder
import com.ineedyourcode.customstopwatch.domain.usecase.StopwatchUsecase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val DEFAULT_TIME_VALUE = "00:00:000"

class StopwatchListOrchestrator(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scopeOne: CoroutineScope,
    private val scopeTwo: CoroutineScope,
) : StopwatchUsecase, StopwatchOrchestratorUsecase {

    private var jobOne: Job? = null
    private var jobTwo: Job? = null
    private val mutableTickerOne = MutableStateFlow("")
    private val mutableTickerTwo = MutableStateFlow("")
    val tickerOne: StateFlow<String> = mutableTickerOne
    val tickerTwo: StateFlow<String> = mutableTickerTwo

    override fun start(stopwatchNumber: StopwatchNumber) {
        when (stopwatchNumber) {
            StopwatchNumber.STOPWATCH_ONE -> {
                if (jobOne == null) {
                    scopeOne.launch {
                        while (isActive) {
                            mutableTickerOne.value =
                                stopwatchStateHolder.getStringTimeRepresentationOne()
                            delay(20)
                        }
                    }
                }

            }

            StopwatchNumber.STOPWATCH_TWO -> {
                if (jobTwo == null) {
                    scopeTwo.launch {
                        while (isActive) {
                            mutableTickerTwo.value =
                                stopwatchStateHolder.getStringTimeRepresentationTwo()
                            delay(20)
                        }
                    }
                }
            }
        }
        stopwatchStateHolder.start(stopwatchNumber)
    }

    override fun pause(stopwatchNumber: StopwatchNumber) {
        stopwatchStateHolder.pause(stopwatchNumber)
        stopJob(stopwatchNumber)
    }

    override fun stop(stopwatchNumber: StopwatchNumber) {
        stopwatchStateHolder.stop(stopwatchNumber)
        stopJob(stopwatchNumber)
        clearValue(stopwatchNumber)
    }

    override fun stopJob(stopwatchNumber: StopwatchNumber) {
        when (stopwatchNumber) {
            StopwatchNumber.STOPWATCH_ONE -> {
                scopeOne.coroutineContext.cancelChildren()
                jobOne = null
            }

            StopwatchNumber.STOPWATCH_TWO -> {
                scopeOne.coroutineContext.cancelChildren()
                jobTwo = null
            }
        }
    }

    override fun clearValue(stopwatchNumber: StopwatchNumber) {
        when (stopwatchNumber) {
            StopwatchNumber.STOPWATCH_ONE -> {
                mutableTickerOne.value = DEFAULT_TIME_VALUE
            }

            StopwatchNumber.STOPWATCH_TWO -> {
                mutableTickerTwo.value = DEFAULT_TIME_VALUE
            }
        }
    }
}