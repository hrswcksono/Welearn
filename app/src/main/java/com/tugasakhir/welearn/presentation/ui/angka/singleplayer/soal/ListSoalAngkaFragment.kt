package com.tugasakhir.welearn.presentation.ui.angka.singleplayer.soal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentListSoalAngkaBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.canvas.*
import com.tugasakhir.welearn.presentation.viewmodel.singleplayer.ListSoalAngkaViewModel
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
            view.findNavController().navigate(ListSoalAngkaFragmentDirections.backLevelAngka())
        }


        sessionManager = SharedPreference(requireContext())

        showGridSoalAngka()
    }

    private fun showGridSoalAngka() {
        binding.progressBar.visibility = View.VISIBLE
        val soal_angka_adapter = ListSoalAngkaAdapter()

        val levelAngka = ListSoalAngkaFragmentArgs.fromBundle(arguments as Bundle).idLevel

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.randomAngka(levelAngka, sessionManager.fetchAuthToken().toString()).collectLatest {
                    soal_angka_adapter.setData(it)
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        soal_angka_adapter.onItemClick = {
            moveDrawingActivity(it)
        }

        with(binding.rvSoalAngka) {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(false)
            adapter = soal_angka_adapter
        }
    }

    private fun moveDrawingActivity(soal: Soal) {
        when(soal.id_level){
            0 -> {
                val moveToLevelNolActivity = Intent(activity, AngkaLevelNolActivity::class.java)
                moveToLevelNolActivity.putExtra(AngkaLevelNolActivity.EXTRA_SOAL, soal.id_soal)
                moveToLevelNolActivity.putExtra(AngkaLevelNolActivity.GAME_MODE, "single")
                startActivity(moveToLevelNolActivity)
            }
            1 -> {
                val moveToLevelSatuActivity = Intent(activity, AngkaLevelSatuActivity::class.java)
                moveToLevelSatuActivity.putExtra(AngkaLevelSatuActivity.EXTRA_SOAL, soal.id_soal)
                moveToLevelSatuActivity.putExtra(AngkaLevelSatuActivity.GAME_MODE, "single")
                startActivity(moveToLevelSatuActivity)
            }
            2 -> {
                val moveToLevelDuaActivity = Intent(activity, AngkaLevelDuaActivity::class.java)
                moveToLevelDuaActivity.putExtra(AngkaLevelDuaActivity.EXTRA_SOAL, soal.id_soal)
                moveToLevelDuaActivity.putExtra(AngkaLevelDuaActivity.GAME_MODE, "single")
                startActivity(moveToLevelDuaActivity)
            }
            3 -> {
                val moveToLevelTigaActivity = Intent(activity, AngkaLevelTigaActivity::class.java)
                moveToLevelTigaActivity.putExtra(AngkaLevelTigaActivity.EXTRA_SOAL, soal.id_soal)
                moveToLevelTigaActivity.putExtra(AngkaLevelTigaActivity.GAME_MODE, "single")
                startActivity(moveToLevelTigaActivity)
            }
            4 -> {
                val moveToLevelEmpatActivity = Intent(activity, AngkaLevelEmpatActivity::class.java)
                moveToLevelEmpatActivity.putExtra(AngkaLevelEmpatActivity.EXTRA_SOAL, soal.id_soal)
                moveToLevelEmpatActivity.putExtra(AngkaLevelEmpatActivity.GAME_MODE, "single")
                startActivity(moveToLevelEmpatActivity)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}