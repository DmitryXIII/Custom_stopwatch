package com.ineedyourcode.customstopwatch.domain

class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter
) {

    var currentStateOne: StopwatchState = StopwatchState.Paused(0)
        private set

    var currentStateTwo: StopwatchState = StopwatchState.Paused(0)
        private set

    fun startOne() {
        currentStateOne = stopwatchStateCalculator.calculateRunningState(currentStateOne)
    }

    fun pauseOne() {
        currentStateOne = stopwatchStateCalculator.calculatePausedState(currentStateOne)
    }

    fun stopOne() {
        currentStateOne = StopwatchState.Paused(0)
    }

    fun startTwo() {
        currentStateTwo = stopwatchStateCalculator.calculateRunningState(currentStateTwo)
    }

    fun pauseTwo() {
        currentStateTwo = stopwatchStateCalculator.calculatePausedState(currentStateTwo)
    }

    fun stopTwo() {
        currentStateTwo = StopwatchState.Paused(0)
    }

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
}