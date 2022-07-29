package com.example.timer.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.timer.R
import com.example.timer.databinding.FragmentTimerBinding

class TimerFragment : Fragment(R.layout.fragment_timer) {

    private lateinit var binding: FragmentTimerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)
    }
}