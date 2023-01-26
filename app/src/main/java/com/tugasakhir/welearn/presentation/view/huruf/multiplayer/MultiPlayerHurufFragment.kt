package com.tugasakhir.welearn.presentation.view.huruf.multiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tugasakhir.welearn.databinding.FragmentMultiPlayerHurufBinding

class MultiPlayerHurufFragment : Fragment() {

    private var _binding: FragmentMultiPlayerHurufBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMultiPlayerHurufBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRoomHuruf.setOnClickListener {
            view.findNavController().navigate(MultiPlayerHurufFragmentDirections.toMatchHuruf())
        }

        binding.btnGabungAngka.setOnClickListener {
            view.findNavController().navigate(MultiPlayerHurufFragmentDirections.toGabungHuruf())
        }
    }
}