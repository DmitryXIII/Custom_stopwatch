package com.ineedyourcode.customstopwatch.domain.usecase

import com.ineedyourcode.customstopwatch.domain.StopwatchNumber

interface StopwatchOrchestratorUsecase {
    fun stopJob(stopwatchNumber: StopwatchNumber)
    fun clearValue(stopwatchNumber: StopwatchNumber)
}