package com.ineedyourcode.customstopwatch.ui.stopwatch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ineedyourcode.customstopwatch.R
import com.ineedyourcode.customstopwatch.databinding.FragmentStopwatchBinding
import com.ineedyourcode.customstopwatch.domain.StopwatchNumber
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StopwatchFragment : Fragment(R.layout.fragment_stopwatch) {
    private var _binding: FragmentStopwatchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StopwatchFragmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStopwatchBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch { subscribeStopwatchOneTime() }
                launch { subscribeStopwatchTwoTime() }
                launch { subscribeStopwatchOneArrow() }
                launch { subscribeStopwatchTwoArrow() }
            }
        }

        binding.stopwatch1StartButton.setOnClickListener {
            viewModel.start(StopwatchNumber.STOPWATCH_ONE)
        }

        binding.stopwatch1PauseButton.setOnClickListener {
            viewModel.pause(StopwatchNumber.STOPWATCH_ONE)
        }

        binding.stopwatch1StopButton.setOnClickListener {
            viewModel.stop(StopwatchNumber.STOPWATCH_ONE)
        }

        binding.stopwatch2StartButton.setOnClickListener {
            viewModel.start(StopwatchNumber.STOPWATCH_TWO)
        }

        binding.stopwatch2PauseButton.setOnClickListener {
            viewModel.pause(StopwatchNumber.STOPWATCH_TWO)
        }

        binding.stopwatch2StopButton.setOnClickListener {
            viewModel.stop(StopwatchNumber.STOPWATCH_TWO)
        }
    }

    private suspend fun subscribeStopwatchOneTime() {
        viewModel.stopwatchOneDisplay.collect {
            binding.stopwatch1DisplayTextView.text = it
        }
    }

    private suspend fun subscribeStopwatchTwoTime() {
        viewModel.stopwatchTwoDisplay.collect {
            binding.stopwatch2DisplayTextView.text = it
        }
    }

    private suspend fun subscribeStopwatchOneArrow() {
        viewModel.stopwatchOneArrow.collect {
            binding.stopwatch1ArrowView.rotation = it
        }
    }

    private suspend fun subscribeStopwatchTwoArrow() {
        viewModel.stopwatchTwoArrow.collect {
            binding.stopwatch2ArrowView.rotation = it
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}