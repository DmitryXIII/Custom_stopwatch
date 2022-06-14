package com.ineedyourcode.customstopwatch.ui.stopwatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ineedyourcode.customstopwatch.data.StopwatchListOrchestrator
import com.ineedyourcode.customstopwatch.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private const val ARROW_TICKS_COUNT = 600
private const val ARROW_ROTATION_ANGLE = 360f / ARROW_TICKS_COUNT
private const val DEFAULT_PREVIOUS_TIME = "0"

class StopwatchFragmentViewModel : ViewModel() {

    private val stopwatchTimeOne: MutableLiveData<String> = MutableLiveData()
    private val stopwatchTimeTwo: MutableLiveData<String> = MutableLiveData()
    private val stopwatchArrowOne: MutableLiveData<Float> = MutableLiveData()
    private val stopwatchArrowTwo: MutableLiveData<Float> = MutableLiveData()


    private val timestampProvider = object : TimestampProvider {
        override fun getMilliseconds(): Long {
            return System.currentTimeMillis()
        }
    }

    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(Dispatchers.IO + SupervisorJob()),
        CoroutineScope(Dispatchers.IO + SupervisorJob())
    )

    fun getStopwatchTimeTwo(): LiveData<String> {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            var previousTimeInSeconds = DEFAULT_PREVIOUS_TIME
            stopwatchListOrchestrator.tickerTwo.collect {
                if (it.isNotEmpty()) {
                    if (it.substring(6, 7) != previousTimeInSeconds) {
                        stopwatchArrowTwo.postValue(ARROW_ROTATION_ANGLE)
                        previousTimeInSeconds = it.substring(6, 7)
                    }
                    stopwatchTimeTwo.postValue(it)
                }
            }
        }
        return stopwatchTimeTwo
    }

    fun getStopwatchTimeOne(): LiveData<String> {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            var previousTimeInSeconds = DEFAULT_PREVIOUS_TIME
            stopwatchListOrchestrator.tickerOne.collect {
                if (it.isNotEmpty()) {
                    if (it.substring(6, 7) != previousTimeInSeconds) {
                        stopwatchArrowOne.postValue(ARROW_ROTATION_ANGLE)
                        previousTimeInSeconds = it.substring(6, 7)
                    }
                    stopwatchTimeOne.postValue(it)
                }
            }
        }
        return stopwatchTimeOne
    }

    fun getStopwatchArrowOne(): LiveData<Float> = stopwatchArrowOne
    fun getStopwatchArrowTwo(): LiveData<Float> = stopwatchArrowTwo

    fun start(stopwatchNumber: StopwatchNumber) {
        stopwatchListOrchestrator.start(stopwatchNumber)
    }

    fun pause(stopwatchNumber: StopwatchNumber) {
        stopwatchListOrchestrator.pause(stopwatchNumber)
    }

    fun stop(stopwatchNumber: StopwatchNumber) {
        stopwatchListOrchestrator.stop(stopwatchNumber)
    }
}