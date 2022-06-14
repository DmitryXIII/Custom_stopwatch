package com.ineedyourcode.customstopwatch.ui.stopwatch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ineedyourcode.customstopwatch.R
import com.ineedyourcode.customstopwatch.databinding.FragmentStopwatchBinding
import com.ineedyourcode.customstopwatch.domain.StopwatchNumber

class StopwatchFragment : Fragment(R.layout.fragment_stopwatch) {
    private var _binding: FragmentStopwatchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StopwatchFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStopwatchBinding.bind(view)

        viewModel.getStopwatchTimeOne().observe(viewLifecycleOwner) {
            binding.stopwatch1DisplayTextView.text = it

        }

        viewModel.getStopwatchTimeTwo().observe(viewLifecycleOwner) {
            binding.stopwatch2DisplayTextView.text = it

        }

        viewModel.getStopwatchArrowOne().observe(viewLifecycleOwner) {
            binding.stopwatch1ArrowView.rotation += it
        }

        viewModel.getStopwatchArrowTwo().observe(viewLifecycleOwner) {
            binding.stopwatch2ArrowView.rotation += it
        }

        binding.stopwatch1StartButton.setOnClickListener {
            viewModel.start(StopwatchNumber.STOPWATCH_ONE)
        }

        binding.stopwatch1PauseButton.setOnClickListener {
            viewModel.pause(StopwatchNumber.STOPWATCH_ONE)
        }

        binding.stopwatch1StopButton.setOnClickListener {
            binding.stopwatch1ArrowView.rotation = 0f
            viewModel.stop(StopwatchNumber.STOPWATCH_ONE)
        }

        binding.stopwatch2StartButton.setOnClickListener {
            viewModel.start(StopwatchNumber.STOPWATCH_TWO)
        }

        binding.stopwatch2PauseButton.setOnClickListener {
            viewModel.pause(StopwatchNumber.STOPWATCH_TWO)
        }

        binding.stopwatch2StopButton.setOnClickListener {
            binding.stopwatch2ArrowView.rotation = 0f
            viewModel.stop(StopwatchNumber.STOPWATCH_TWO)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}