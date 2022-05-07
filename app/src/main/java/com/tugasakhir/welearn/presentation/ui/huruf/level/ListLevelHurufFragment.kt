package com.tugasakhir.welearn.presentation.ui.huruf.level

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.LevelData
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentListLevelHurufBinding
import com.tugasakhir.welearn.presentation.ui.angka.soal.ListSoalAngkaFragmentDirections
import com.tugasakhir.welearn.presentation.ui.angka.soal.ListSoalAngkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class ListLevelHurufFragment : Fragment() {

    private var _binding: FragmentListLevelHurufBinding? = null
    private val binding get() = _binding!!
//    private val viewModel: ListSoalAngkaViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListLevelHurufBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listHurufBack.setOnClickListener {
            view.findNavController().navigate(R.id.back_mode_huruf)
        }

        sessionManager = SharedPreference(requireContext())

        showGridHuruf()
    }

    private fun showGridHuruf(){
        val huruf_adapter = ListLevelHurufAdapter()

        huruf_adapter.onItemClick = {
            val toSoalHuruf = ListLevelHurufFragmentDirections.toSoalHuruf()
            toSoalHuruf.idLevel = it.id_level
            view?.findNavController()?.navigate(toSoalHuruf)
        }

        huruf_adapter.setData(LevelData.listLevelHuruf)
        with(binding.rvLevelHuruf) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(false)
            adapter = huruf_adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}