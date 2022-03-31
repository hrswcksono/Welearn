package com.tugasakhir.welearn.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tugasakhir.welearn.databinding.FragmentHomeBinding
import com.tugasakhir.welearn.presentation.ui.question.AngkaActivity
import com.tugasakhir.welearn.presentation.ui.question.HurufActivity

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
//        binding.imgHitung.setOnClickListener {
//            activity?.let {
//                val intent = Intent(it, AngkaActivity::class.java)
//                it.startActivity(intent)
//            }
//        }
//        binding.imgTulis.setOnClickListener {
//            val intent = Intent(getActivity(), HurufActivity::class.java)
//            startActivity(intent)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}