package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.FragmentAngkaLevelTigaBinding

class AngkaLevelTigaFragment : Fragment() {

    private var _binding: FragmentAngkaLevelTigaBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAngkaLevelTigaBinding.inflate(inflater, container, false)
        return binding.root
    }
}