package com.tugasakhir.welearn.presentation.view.angka.multiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tugasakhir.welearn.databinding.FragmentMultiPlayerAngkaBinding

class MultiPlayerAngkaFragment : Fragment() {

    private var _binding: FragmentMultiPlayerAngkaBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMultiPlayerAngkaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRoomAngka.setOnClickListener {
            view.findNavController().navigate(MultiPlayerAngkaFragmentDirections.toMatchAngka())
        }

        binding.btnGabungAngka.setOnClickListener {
            view.findNavController().navigate(MultiPlayerAngkaFragmentDirections.toGabungAngka())
        }

        binding.backAngkaMulti.setOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}