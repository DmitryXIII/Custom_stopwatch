package com.ineedyourcode.customstopwatch.data

import com.ineedyourcode.customstopwatch.domain.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StopwatchListOrchestrator(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scopeOne: CoroutineScope,
    private val scopeTwo: CoroutineScope,
) {

    private var jobOne: Job? = null
    private var jobTwo: Job? = null
    private val mutableTickerOne = MutableStateFlow("")
    private val mutableTickerTwo = MutableStateFlow("")
    val tickerOne: StateFlow<String> = mutableTickerOne
    val tickerTwo: StateFlow<String> = mutableTickerTwo

    fun startOne() {
        if (jobOne == null) {
            scopeOne.launch {
                while (isActive) {
                    mutableTickerOne.value = stopwatchStateHolder.getStringTimeRepresentationOne()
                    delay(20)
                }
            }
        }
        stopwatchStateHolder.startOne()
    }

    fun startTwo() {
        if (jobTwo == null) {
            scopeTwo.launch {
                while (isActive) {
                    mutableTickerTwo.value = stopwatchStateHolder.getStringTimeRepresentationTwo()
                    delay(20)
                }
            }
        }
        stopwatchStateHolder.startTwo()
    }

    fun pauseOne() {
        stopwatchStateHolder.pauseOne()
        stopJobOne()
    }

    fun pauseTwo() {
        stopwatchStateHolder.pauseTwo()
        stopJobTwo()
    }

    fun stopOne() {
        stopwatchStateHolder.stopOne()
        stopJobOne()
        clearValueOne()
    }

    fun stopTwo() {
        stopwatchStateHolder.stopTwo()
        stopJobTwo()
        clearValueTwo()
    }

    private fun stopJobOne() {
        scopeOne.coroutineContext.cancelChildren()
        jobOne = null
    }

    private fun stopJobTwo() {
        scopeOne.coroutineContext.cancelChildren()
        jobTwo = null
    }

    private fun clearValueOne() {
        mutableTickerOne.value = "00:00:000"
    }

    private fun clearValueTwo() {
        mutableTickerTwo.value = "00:00:000"
    }
}