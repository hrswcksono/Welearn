package com.tugasakhir.welearn.presentation.ui.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.FragmentProfileBinding
import com.tugasakhir.welearn.databinding.FragmentScoreHurufBinding


class ScoreHurufFragment : Fragment() {

    private var _binding: FragmentScoreHurufBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScoreHurufBinding.inflate(inflater, container, false)
        return binding.root
    }
}