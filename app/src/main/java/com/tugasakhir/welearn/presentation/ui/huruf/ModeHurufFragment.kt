package com.tugasakhir.welearn.presentation.ui.huruf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.FragmentModeHurufBinding

class ModeHurufFragment : Fragment() {

    private var _binding: FragmentModeHurufBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentModeHurufBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.modeHurufBack.setOnClickListener {
            view.findNavController().navigate(R.id.back_to_home_mode_huruf)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}