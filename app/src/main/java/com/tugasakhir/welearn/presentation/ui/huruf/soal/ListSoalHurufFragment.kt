package com.tugasakhir.welearn.presentation.ui.huruf.soal

import android.content.Intent
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
import com.tugasakhir.welearn.databinding.FragmentListSoalHurufBinding
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelNolActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel


class ListSoalHurufFragment : Fragment() {

    private var _binding: FragmentListSoalHurufBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListSoalHurufViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

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

        sessionManager = SharedPreference(requireContext())

        showGridSoalHuruf()
    }

    private fun showGridSoalHuruf() {
        val soal_huruf_adapter = ListSoalHurufAdapter()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.randomHuruf(1, sessionManager.fetchAuthToken().toString()).collectLatest {
                    soal_huruf_adapter.setData(it)
                }
            }
        }

        soal_huruf_adapter.onItemClick = {
            startActivity(Intent(activity, HurufLevelNolActivity::class.java))
        }

//        soal_huruf_adapter.setData(LevelData.listLevel)
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