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
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelDuaActivity
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelNolActivity
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelSatuActivity
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelTigaActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelDuaActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelNolActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelSatuActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelTIgaActivity
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
            view.findNavController().navigate(ListSoalHurufFragmentDirections.backLevelHuruf())
        }

        sessionManager = SharedPreference(requireContext())

        showGridSoalHuruf()
    }

    private fun showGridSoalHuruf() {
        val soal_huruf_adapter = ListSoalHurufAdapter()

        val levelHuruf = ListSoalHurufFragmentArgs.fromBundle(arguments as Bundle).idLevel

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.randomHuruf(levelHuruf, sessionManager.fetchAuthToken().toString()).collectLatest {
                    soal_huruf_adapter.setData(it)
                }
            }
        }

        soal_huruf_adapter.onItemClick = {
            moveDrawingActivity(it)
        }

        with(binding.rvSoalHuruf) {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(false)
            adapter = soal_huruf_adapter
        }
    }

    private fun moveDrawingActivity(soal: Soal) {
        when(soal.id_level){
            0 -> {
                val moveToLevelNolActivity = Intent(activity, HurufLevelNolActivity::class.java)
                moveToLevelNolActivity.putExtra(HurufLevelNolActivity.EXTRA_SOAL, soal)
                startActivity(moveToLevelNolActivity)
            }
            1 -> {
                val moveToLevelSatuActivity = Intent(activity, HurufLevelSatuActivity::class.java)
                moveToLevelSatuActivity.putExtra(HurufLevelSatuActivity.EXTRA_SOAL, soal)
                startActivity(moveToLevelSatuActivity)
            }
            2 -> {
                val moveToLevelDuaActivity = Intent(activity, HurufLevelDuaActivity::class.java)
                moveToLevelDuaActivity.putExtra(HurufLevelDuaActivity.EXTRA_SOAL, soal)
                startActivity(moveToLevelDuaActivity)
            }
            3 -> {
                val moveToLevelTigaActivity = Intent(activity, HurufLevelTIgaActivity::class.java)
                moveToLevelTigaActivity.putExtra(HurufLevelTIgaActivity.EXTRA_SOAL, soal)
                startActivity(moveToLevelTigaActivity)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}