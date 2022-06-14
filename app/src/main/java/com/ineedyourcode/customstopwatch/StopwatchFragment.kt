package com.ineedyourcode.customstopwatch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ineedyourcode.customstopwatch.databinding.FragmentStopwatchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val ARROW_DELAY = 100L
private const val ARROW_TICKS_COUNT = 600
private const val ARROW_ROTATION_ANGLE = 360f / ARROW_TICKS_COUNT

class StopwatchFragment: Fragment(R.layout.fragment_stopwatch) {
    private var _binding: FragmentStopwatchBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStopwatchBinding.bind(view)

        binding.stopwatch1StartButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                repeat(ARROW_TICKS_COUNT) {
                    delay(ARROW_DELAY)
                    binding.stopwatch1ArrowView.rotation += ARROW_ROTATION_ANGLE
                }
            }
        }

        binding.stopwatch2StartButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                repeat(ARROW_TICKS_COUNT) {
                    delay(ARROW_DELAY)
                    binding.stopwatch2ArrowView.rotation += ARROW_ROTATION_ANGLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}