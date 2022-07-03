package com.tugasakhir.welearn.presentation.ui.huruf.singleplayer.soal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.databinding.FragmentListSoalHurufBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelDuaActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelNolActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelSatuActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelTigaActivity
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ListSoalHurufViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class ListSoalHurufFragment : Fragment() {

    private var _binding: FragmentListSoalHurufBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListSoalHurufViewModel by viewModel()

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
            view.findNavController().navigate(ListSoalHurufFragmentDirections.backLevelHuruf())
        }

        showGridSoalHuruf()
    }

    private fun showGridSoalHuruf() {
        binding.progressBar3.visibility = View.VISIBLE
        val soalHurufAdapter = ListSoalHurufAdapter()

        val levelHuruf = ListSoalHurufFragmentArgs.fromBundle(arguments as Bundle).idLevel

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.randomHuruf(levelHuruf).collectLatest {
                    soalHurufAdapter.setData(it)
                    binding.progressBar3.visibility = View.INVISIBLE
                }
            }
        }

        soalHurufAdapter.onItemClick = {
            moveDrawingActivity(it)
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
                val moveToLevelNolActivity = Intent(activity, HurufLevelNolActivity::class.java)
                moveToLevelNolActivity.putExtra(HurufLevelNolActivity.EXTRA_SOAL, soal.idSoal)
                moveToLevelNolActivity.putExtra(HurufLevelNolActivity.GAME_MODE, "single")
                startActivity(moveToLevelNolActivity)
            }
            1 -> {
                val moveToLevelSatuActivity = Intent(activity, HurufLevelSatuActivity::class.java)
                moveToLevelSatuActivity.putExtra(HurufLevelSatuActivity.EXTRA_SOAL, soal.idSoal)
                moveToLevelSatuActivity.putExtra(HurufLevelSatuActivity.GAME_MODE, "single")
                startActivity(moveToLevelSatuActivity)
            }
            2 -> {
                val moveToLevelDuaActivity = Intent(activity, HurufLevelDuaActivity::class.java)
                moveToLevelDuaActivity.putExtra(HurufLevelDuaActivity.EXTRA_SOAL, soal.idSoal)
                moveToLevelDuaActivity.putExtra(HurufLevelDuaActivity.GAME_MODE, "single")
                startActivity(moveToLevelDuaActivity)
            }
            3 -> {
                val moveToLevelTigaActivity = Intent(activity, HurufLevelTigaActivity::class.java)
                moveToLevelTigaActivity.putExtra(HurufLevelTigaActivity.EXTRA_SOAL, soal.idSoal)
                moveToLevelTigaActivity.putExtra(HurufLevelTigaActivity.GAME_MODE, "single")
                startActivity(moveToLevelTigaActivity)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}