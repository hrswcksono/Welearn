package com.tugasakhir.welearn.presentation.ui.score.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.FragmentScoreHurufUserBinding

class ScoreHurufUserFragment : Fragment() {

    private var _binding: FragmentScoreHurufUserBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScoreHurufUserBinding.inflate(inflater, container, false)
        return binding.root
    }

}