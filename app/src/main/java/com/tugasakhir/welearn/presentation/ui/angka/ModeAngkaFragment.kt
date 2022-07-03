package com.tugasakhir.welearn.presentation.ui.angka

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tugasakhir.welearn.databinding.FragmentModeAngkaBinding
import com.tugasakhir.welearn.presentation.ui.angka.multiplayer.MatchAngkaActivity

class ModeAngkaFragment : Fragment() {

    private var _binding: FragmentModeAngkaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModeAngkaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSingleAngka.setOnClickListener {
            view.findNavController().navigate(ModeAngkaFragmentDirections.toLevelAngka())
        }

        binding.btnMultiAngka.setOnClickListener {
            startActivity(Intent(activity, MatchAngkaActivity::class.java))
        }

        binding.modeAngkaBack.setOnClickListener {
            view.findNavController().navigate(ModeAngkaFragmentDirections.backToHomeModeAngka())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}