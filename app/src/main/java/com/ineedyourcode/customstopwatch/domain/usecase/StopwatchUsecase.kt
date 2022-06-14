package com.ineedyourcode.customstopwatch.domain.usecase

import com.ineedyourcode.customstopwatch.domain.StopwatchNumber

interface StopwatchUsecase {
    fun start(stopwatchNumber: StopwatchNumber)
    fun pause(stopwatchNumber: StopwatchNumber)
    fun stop(stopwatchNumber: StopwatchNumber)
}