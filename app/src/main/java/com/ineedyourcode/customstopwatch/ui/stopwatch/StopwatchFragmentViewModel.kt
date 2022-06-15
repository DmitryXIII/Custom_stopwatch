package com.ineedyourcode.customstopwatch.ui.stopwatch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ineedyourcode.customstopwatch.data.StopwatchListOrchestrator
import com.ineedyourcode.customstopwatch.domain.StopwatchNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val DEFAULT_CURRENT_TIME = "00:00:000"
private const val DEFAULT_ARROW_ANGLE = 0f
private const val ARROW_TICKS_COUNT = 600
private const val ARROW_ROTATION_ANGLE = 360f / ARROW_TICKS_COUNT
private const val DEFAULT_PREVIOUS_ONE_TENTH_VALUE = '0'
private const val TARGET_CHAR_INDEX = 6

class StopwatchFragmentViewModel(
    private val stopwatchListOrchestrator: StopwatchListOrchestrator,
) : ViewModel() {

    private val _stopwatchOneDisplay: MutableStateFlow<String> = MutableStateFlow(DEFAULT_CURRENT_TIME)
    private val _stopwatchTwoDisplay: MutableStateFlow<String> = MutableStateFlow(DEFAULT_CURRENT_TIME)
    private val _stopwatchOneArrow: MutableStateFlow<Float> = MutableStateFlow(DEFAULT_ARROW_ANGLE)
    private val _stopwatchTwoArrow: MutableStateFlow<Float> = MutableStateFlow(DEFAULT_ARROW_ANGLE)
    private var currentArrowOneAngle = DEFAULT_ARROW_ANGLE
    private var currentArrowTwoAngle = DEFAULT_ARROW_ANGLE

    val stopwatchOneDisplay: StateFlow<String> = _stopwatchOneDisplay
    val stopwatchTwoDisplay: StateFlow<String> = _stopwatchTwoDisplay
    val stopwatchOneArrow: StateFlow<Float> = _stopwatchOneArrow
    val stopwatchTwoArrow: StateFlow<Float> = _stopwatchTwoArrow

    init {
        viewModelScope.launch {
            var previousOneTenthOfASecond = DEFAULT_PREVIOUS_ONE_TENTH_VALUE
            stopwatchListOrchestrator.tickerOne.collect {
                if (it.toCharArray()[TARGET_CHAR_INDEX] != previousOneTenthOfASecond) {
                    currentArrowOneAngle += ARROW_ROTATION_ANGLE
                    _stopwatchOneArrow.tryEmit(currentArrowOneAngle)
                    previousOneTenthOfASecond = it.toCharArray()[TARGET_CHAR_INDEX]
                }
                _stopwatchOneDisplay.tryEmit(it)
                Log.d("@@@@@@", it)
            }
        }

        viewModelScope.launch {
            var previousOneTenthOfASecond = DEFAULT_PREVIOUS_ONE_TENTH_VALUE
            stopwatchListOrchestrator.tickerTwo.collect {
                if (it.toCharArray()[TARGET_CHAR_INDEX] != previousOneTenthOfASecond) {
                    currentArrowTwoAngle += ARROW_ROTATION_ANGLE
                    _stopwatchTwoArrow.tryEmit(currentArrowTwoAngle)
                    previousOneTenthOfASecond = it.toCharArray()[TARGET_CHAR_INDEX]
                }
                _stopwatchTwoDisplay.tryEmit(it)
            }
        }
    }

    fun start(stopwatchNumber: StopwatchNumber) {
        stopwatchListOrchestrator.start(stopwatchNumber)
    }

    fun pause(stopwatchNumber: StopwatchNumber) {
        stopwatchListOrchestrator.pause(stopwatchNumber)
    }

    fun stop(stopwatchNumber: StopwatchNumber) {
        when (stopwatchNumber) {
            StopwatchNumber.STOPWATCH_ONE -> {
                currentArrowOneAngle = DEFAULT_ARROW_ANGLE
                _stopwatchOneArrow.tryEmit(currentArrowOneAngle)
            }
            StopwatchNumber.STOPWATCH_TWO -> {
                currentArrowTwoAngle = DEFAULT_ARROW_ANGLE
                _stopwatchTwoArrow.tryEmit(currentArrowTwoAngle)
            }
        }
        stopwatchListOrchestrator.stop(stopwatchNumber)
    }
}