package com.ineedyourcode.customstopwatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ineedyourcode.customstopwatch.R
import com.ineedyourcode.customstopwatch.ui.stopwatch.StopwatchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, StopwatchFragment())
                .commit()
        }
    }
}







