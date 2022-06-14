package com.ineedyourcode.customstopwatch.di

import com.ineedyourcode.customstopwatch.data.StopwatchListOrchestrator
import com.ineedyourcode.customstopwatch.domain.*
import com.ineedyourcode.customstopwatch.ui.stopwatch.StopwatchFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { StopwatchFragmentViewModel(stopwatchListOrchestrator = get()) }

    single {
        val timestampProvider = object : TimestampProvider {
            override fun getMilliseconds(): Long {
                return System.currentTimeMillis()
            }
        }

        StopwatchListOrchestrator(
            StopwatchStateHolder(
                StopwatchStateCalculator(timestampProvider,
                    ElapsedTimeCalculator(timestampProvider)),
                ElapsedTimeCalculator(timestampProvider), TimestampMillisecondsFormatter()),
            CoroutineScope(Dispatchers.IO + SupervisorJob()),
            CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}