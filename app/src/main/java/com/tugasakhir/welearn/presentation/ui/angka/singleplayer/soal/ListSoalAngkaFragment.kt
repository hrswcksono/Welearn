package com.tugasakhir.welearn.presentation.ui.angka.singleplayer.soal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.databinding.FragmentListSoalAngkaBinding
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.ui.angka.canvas.*
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ListSoalRandomPresenter
import com.tugasakhir.welearn.presentation.ui.angka.singleplayer.level.ListLevelAngkaFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListSoalAngkaFragment : Fragment() {

    private var _binding: FragmentListSoalAngkaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListSoalRandomPresenter by viewModel()

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
            view.findNavController().navigate(ListSoalAngkaFragmentDirections.backLevelAngka())
        }

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
                viewModel.randomSoalSingle(2 ,levelAngka)
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

    private fun moveDrawingActivity(soal: SoalEntity) {
        when(soal.idLevel){
            0 -> {
//                val moveToLevelNolActivity = Intent(activity, AngkaLevelNolActivity::class.java)
//                moveToLevelNolActivity.putExtra(AngkaLevelNolActivity.EXTRA_SOAL, soal.idSoal)
//                moveToLevelNolActivity.putExtra(AngkaLevelNolActivity.GAME_MODE, "single")
//                startActivity(moveToLevelNolActivity)
                val toLevelNolAngka = ListSoalAngkaFragmentDirections.toAngkaLevelNol()
                toLevelNolAngka.idSoal = soal.idSoal
                toLevelNolAngka.gameMode = "single"
                view?.findNavController()?.navigate(toLevelNolAngka)
            }
            1 -> {
//                val moveToLevelSatuActivity = Intent(activity, AngkaLevelSatuActivity::class.java)
//                moveToLevelSatuActivity.putExtra(AngkaLevelSatuActivity.EXTRA_SOAL, soal.idSoal)
//                moveToLevelSatuActivity.putExtra(AngkaLevelSatuActivity.GAME_MODE, "single")
//                startActivity(moveToLevelSatuActivity)
                val toLevelSatuAngka = ListSoalAngkaFragmentDirections.toAngkaLevelSatu()
                toLevelSatuAngka.idSoal = soal.idSoal
                toLevelSatuAngka.gameMode = "single"
                view?.findNavController()?.navigate(toLevelSatuAngka)
            }
            2 -> {
//                val moveToLevelDuaActivity = Intent(activity, AngkaLevelDuaActivity::class.java)
//                moveToLevelDuaActivity.putExtra(AngkaLevelDuaActivity.EXTRA_SOAL, soal.idSoal)
//                moveToLevelDuaActivity.putExtra(AngkaLevelDuaActivity.GAME_MODE, "single")
//                startActivity(moveToLevelDuaActivity)
                val toLevelDuaAngka = ListSoalAngkaFragmentDirections.toAngkaLevelDua()
                toLevelDuaAngka.idSoal = soal.idSoal
                toLevelDuaAngka.gameMode = "single"
                view?.findNavController()?.navigate(toLevelDuaAngka)
            }
            3 -> {
//                val moveToLevelTigaActivity = Intent(activity, AngkaLevelTigaActivity::class.java)
//                moveToLevelTigaActivity.putExtra(AngkaLevelTigaActivity.EXTRA_SOAL, soal.idSoal)
//                moveToLevelTigaActivity.putExtra(AngkaLevelTigaActivity.GAME_MODE, "single")
//                startActivity(moveToLevelTigaActivity)
                val toLevelTigaAngka = ListSoalAngkaFragmentDirections.toAngkaLevelTiga()
                toLevelTigaAngka.idSoal = soal.idSoal
                toLevelTigaAngka.gameMode = "single"
                view?.findNavController()?.navigate(toLevelTigaAngka)
            }
            4 -> {
//                val moveToLevelEmpatActivity = Intent(activity, AngkaLevelEmpatActivity::class.java)
//                moveToLevelEmpatActivity.putExtra(AngkaLevelEmpatActivity.EXTRA_SOAL, soal.idSoal)
//                moveToLevelEmpatActivity.putExtra(AngkaLevelEmpatActivity.GAME_MODE, "single")
//                startActivity(moveToLevelEmpatActivity)
                val toLevelEmpatAngka = ListSoalAngkaFragmentDirections.toAngkaLevelEmpat()
                toLevelEmpatAngka.idSoal = soal.idSoal
                toLevelEmpatAngka.gameMode = "single"
                view?.findNavController()?.navigate(toLevelEmpatAngka)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}