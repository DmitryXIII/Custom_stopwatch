package com.ineedyourcode.customstopwatch.domain

import com.ineedyourcode.customstopwatch.domain.usecase.StopwatchUsecase

private const val DEFAULT_VALUE = 0L

class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter,
) : StopwatchUsecase {

    var currentStateOne: StopwatchState = StopwatchState.Paused(DEFAULT_VALUE)
        private set

    var currentStateTwo: StopwatchState = StopwatchState.Paused(DEFAULT_VALUE)
        private set

    fun getStringTimeRepresentationOne(): String {
        val elapsedTime = when (val currentState = currentStateOne) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }

    fun getStringTimeRepresentationTwo(): String {
        val elapsedTime = when (val currentState = currentStateTwo) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }

    override fun start(stopwatchNumber: StopwatchNumber) {
        when (stopwatchNumber) {
            StopwatchNumber.STOPWATCH_ONE -> {
                currentStateOne = stopwatchStateCalculator.calculateRunningState(currentStateOne)
            }

            StopwatchNumber.STOPWATCH_TWO -> {
                currentStateTwo = stopwatchStateCalculator.calculateRunningState(currentStateTwo)
            }
        }
    }

    override fun pause(stopwatchNumber: StopwatchNumber) {
        when (stopwatchNumber) {
            StopwatchNumber.STOPWATCH_ONE -> {
                currentStateOne = stopwatchStateCalculator.calculatePausedState(currentStateOne)
            }

            StopwatchNumber.STOPWATCH_TWO -> {
                currentStateTwo = stopwatchStateCalculator.calculatePausedState(currentStateTwo)
            }
        }
    }

    override fun stop(stopwatchNumber: StopwatchNumber) {
        when (stopwatchNumber) {
            StopwatchNumber.STOPWATCH_ONE -> {
                currentStateOne = StopwatchState.Paused(DEFAULT_VALUE)
            }

            StopwatchNumber.STOPWATCH_TWO -> {
                currentStateTwo = StopwatchState.Paused(DEFAULT_VALUE)
            }
        }
    }
}