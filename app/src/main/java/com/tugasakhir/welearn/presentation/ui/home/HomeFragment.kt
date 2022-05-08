package com.tugasakhir.welearn.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAngka.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.toModeAngka())
        }
        binding.btnHuruf.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.toModeHuruf())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}