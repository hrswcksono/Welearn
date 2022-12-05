package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.FragmentHurufReadyBinding

class HurufReadyFragment : Fragment() {

    private var _binding: FragmentHurufReadyBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHurufReadyBinding.inflate(inflater, container, false)
        return binding.root
    }

}