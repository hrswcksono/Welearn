package com.tugasakhir.welearn.presentation.ui.angka.soal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.LevelData
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentListSoalAngkaBinding
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelNolActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListSoalAngkaFragment : Fragment() {

    private var _binding: FragmentListSoalAngkaBinding? = null
    private val binding get() = _binding!!
//    private val args : FragmentList by navArgs()
    private val viewModel: ListSoalAngkaViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListSoalAngkaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.soalAngkaBack.setOnClickListener {
            view.findNavController().navigate(R.id.back_level_angka)
        }

        sessionManager = SharedPreference(requireContext())

        showGridSoalAngka()
    }

    private fun showGridSoalAngka() {
        val soal_angka_adapter = ListSoalAngkaAdapter()

        val levelAngka = ListSoalAngkaFragmentArgs.fromBundle(arguments as Bundle).idLevel

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.randomAngka(levelAngka, sessionManager.fetchAuthToken().toString()).collectLatest {
                    soal_angka_adapter.setData(it)
                }
            }
        }

        soal_angka_adapter.onItemClick = {
            startActivity(Intent(activity, AngkaLevelNolActivity::class.java))
        }

        with(binding.rvSoalAngka) {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(false)
            adapter = soal_angka_adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}