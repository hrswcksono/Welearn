package com.tugasakhir.welearn.presentation.ui.huruf.soal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.LevelData
import com.tugasakhir.welearn.databinding.FragmentListSoalHurufBinding
import com.tugasakhir.welearn.presentation.ui.huruf.soal.ListSoalHurufAdapter


class ListSoalHurufFragment : Fragment() {

    private var _binding: FragmentListSoalHurufBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListSoalHurufBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.soalHurufBack.setOnClickListener {
            view.findNavController().navigate(R.id.back_level_huruf)
        }

        showGridSoalHuruf()
    }

    private fun showGridSoalHuruf() {
        val soal_huruf_adapter = ListSoalHurufAdapter()
        soal_huruf_adapter.setData(LevelData.listLevel)
        with(binding.rvSoalHuruf) {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(false)
            adapter = soal_huruf_adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}