package com.tugasakhir.welearn.presentation.view.angka.singleplayer.soal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentListSoalAngkaBinding
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ListSoalRandomPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListSoalAngkaFragment : Fragment() {

    private var _binding: FragmentListSoalAngkaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListSoalRandomPresenter by viewModel()
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
            view.findNavController().navigate(ListSoalAngkaFragmentDirections.backToLevelFromSoalAngka())
        }

        sessionManager = activity?.let { SharedPreference(it) }!!

        showGridSoalAngka()
    }

    private fun showGridSoalAngka() {
        binding.progressBar.visibility = View.VISIBLE
        val soalAngkaAdapter = ListSoalAngkaAdapter()
        soalAngkaAdapter.onItemClick = {
            moveDrawingActivity(it)
        }

        val levelAngka = ListSoalAngkaFragmentArgs.fromBundle(arguments as Bundle).idLevel

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.randomSoalSingle(2 ,levelAngka, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        soalAngkaAdapter.setData(it)
                        binding.progressBar.visibility = View.GONE
                }
            }
        }

        with(binding.rvSoalAngka) {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(false)
            adapter = soalAngkaAdapter
        }
    }

    private fun moveDrawingActivity(soal: Soal) {
        when(soal.idLevel){
            0 -> {
                val toLevelNolAngka = ListSoalAngkaFragmentDirections.toAngkaLevelNol()
                toLevelNolAngka.idSoal = soal.idSoal
                view?.findNavController()?.navigate(toLevelNolAngka)
            }
            1 -> {
                val toLevelSatuAngka = ListSoalAngkaFragmentDirections.toAngkaLevelSatu()
                toLevelSatuAngka.idSoal = soal.idSoal
                view?.findNavController()?.navigate(toLevelSatuAngka)
            }
            2 -> {
                val toLevelDuaAngka = ListSoalAngkaFragmentDirections.toAngkaLevelDua()
                toLevelDuaAngka.idSoal = soal.idSoal
                view?.findNavController()?.navigate(toLevelDuaAngka)
            }
            3 -> {
                val toLevelTigaAngka = ListSoalAngkaFragmentDirections.toAngkaLevelTiga()
                toLevelTigaAngka.idSoal = soal.idSoal
                view?.findNavController()?.navigate(toLevelTigaAngka)
            }
            4 -> {
                val toLevelEmpatAngka = ListSoalAngkaFragmentDirections.toAngkaLevelEmpat()
                toLevelEmpatAngka.idSoal = soal.idSoal
                view?.findNavController()?.navigate(toLevelEmpatAngka)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}