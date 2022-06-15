package com.ineedyourcode.customstopwatch

import android.app.Application
import com.ineedyourcode.customstopwatch.di.appModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}