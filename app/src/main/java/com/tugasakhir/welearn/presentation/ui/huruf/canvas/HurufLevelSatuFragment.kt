package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.FragmentHurufLevelSatuBinding

class HurufLevelSatuFragment : Fragment() {

    private var _binding: FragmentHurufLevelSatuBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHurufLevelSatuBinding.inflate(inflater, container, false)
        return binding.root
    }

}