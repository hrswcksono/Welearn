package com.tugasakhir.welearn.presentation.view.huruf.singleplayer.soal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentListSoalHurufBinding
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ListSoalRandomPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListSoalHurufFragment : Fragment() {

    private var _binding: FragmentListSoalHurufBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListSoalRandomPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListSoalHurufBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.soalHurufBack.setOnClickListener {
            view.findNavController().navigate(ListSoalHurufFragmentDirections.backToLevelFromSoalHuruf())
        }

        sessionManager = activity?.let { SharedPreference(it) }!!
        showGridSoalHuruf()
    }

    private fun showGridSoalHuruf() {
        val soalHurufAdapter = ListSoalHurufAdapter()
        binding.progressBar3.visibility = View.VISIBLE
        soalHurufAdapter.onItemClick = {
            moveDrawingActivity(it)
        }

        val levelHuruf = ListSoalHurufFragmentArgs.fromBundle(arguments as Bundle).idLevel

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.randomSoalSingle(1,levelHuruf, sessionManager.fetchAuthToken()!!).collectLatest {
                    soalHurufAdapter.setData(it)
                    binding.progressBar3.visibility = View.GONE
                }
            }
        }

        with(binding.rvSoalHuruf) {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(false)
            adapter = soalHurufAdapter
        }
    }

    private fun moveDrawingActivity(soal: Soal) {
        when(soal.idLevel){
            0 -> {
                val toLevelNolHuruf = ListSoalHurufFragmentDirections.toHurufLevelNol()
                toLevelNolHuruf.idSoal = soal.idSoal
                view?.findNavController()?.navigate(toLevelNolHuruf)
            }
            1 -> {
                val toLevelSatuHuruf = ListSoalHurufFragmentDirections.toHurufLevelSatu()
                toLevelSatuHuruf.idSoal = soal.idSoal
                view?.findNavController()?.navigate(toLevelSatuHuruf)
            }
            2 -> {
                val toLevelDuaHuruf = ListSoalHurufFragmentDirections.toHurufLevelDua()
                toLevelDuaHuruf.idSoal = soal.idSoal
                view?.findNavController()?.navigate(toLevelDuaHuruf)
            }
            3 -> {
                val toLevelTigaHuruf = ListSoalHurufFragmentDirections.toHurufLevelTiga()
                toLevelTigaHuruf.idSoal = soal.idSoal
                view?.findNavController()?.navigate(toLevelTigaHuruf)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}