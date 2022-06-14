package com.ineedyourcode.customstopwatch.ui.stopwatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ineedyourcode.customstopwatch.data.StopwatchListOrchestrator
import com.ineedyourcode.customstopwatch.domain.StopwatchNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private const val ARROW_TICKS_COUNT = 600
private const val ARROW_ROTATION_ANGLE = 360f / ARROW_TICKS_COUNT
private const val DEFAULT_PREVIOUS_TIME = '0'
private const val TARGET_CHAR_INDEX = 6

class StopwatchFragmentViewModel(
    private val stopwatchListOrchestrator: StopwatchListOrchestrator,
) : ViewModel() {

    private val stopwatchOneTime: MutableLiveData<String> = MutableLiveData()
    private val stopwatchTwoTime: MutableLiveData<String> = MutableLiveData()
    private val stopwatchOneArrow: MutableLiveData<Float> = MutableLiveData()
    private val stopwatchTwoArrow: MutableLiveData<Float> = MutableLiveData()

    fun getStopwatchTimeTwo(): LiveData<String> {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            var previousTimeInSeconds = DEFAULT_PREVIOUS_TIME
            stopwatchListOrchestrator.tickerTwo.collect {
                if (it.toCharArray()[6] != previousTimeInSeconds) {
                    stopwatchTwoArrow.postValue(ARROW_ROTATION_ANGLE)
                    previousTimeInSeconds = it.toCharArray()[TARGET_CHAR_INDEX]
                }
                stopwatchTwoTime.postValue(it)
            }
        }
        return stopwatchTwoTime
    }

    fun getStopwatchTimeOne(): LiveData<String> {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            var previousTimeInSeconds = DEFAULT_PREVIOUS_TIME
            stopwatchListOrchestrator.tickerOne.collect {
                if (it.toCharArray()[6] != previousTimeInSeconds) {
                    stopwatchOneArrow.postValue(ARROW_ROTATION_ANGLE)
                    previousTimeInSeconds = it.toCharArray()[TARGET_CHAR_INDEX]
                }
                stopwatchOneTime.postValue(it)
            }
        }
        return stopwatchOneTime
    }

    fun getStopwatchArrowOne(): LiveData<Float> = stopwatchOneArrow
    fun getStopwatchArrowTwo(): LiveData<Float> = stopwatchTwoArrow

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