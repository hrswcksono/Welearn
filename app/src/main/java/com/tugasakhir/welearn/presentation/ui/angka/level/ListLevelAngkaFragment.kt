package com.tugasakhir.welearn.presentation.ui.angka.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.LevelData
import com.tugasakhir.welearn.databinding.FragmentListLevelAngkaBinding

class ListLevelAngkaFragment : Fragment() {

    private var _binding: FragmentListLevelAngkaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListLevelAngkaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listAngkaBack.setOnClickListener {
            view.findNavController().navigate(R.id.back_mode_angka)
        }

        showGridAngka()
    }

    private fun showGridAngka() {
        val angka_adapter = ListLevelAngkaAdapter()

        angka_adapter.onItemClick = {
            val toSoalAngka = ListLevelAngkaFragmentDirections.toSoalAngka()
            toSoalAngka.idLevel = it.id_level
            view?.findNavController()?.navigate(toSoalAngka)
        }

        angka_adapter.setData(LevelData.listLevel)
        with(binding.rvLevelAngka) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(false)
            adapter = angka_adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}