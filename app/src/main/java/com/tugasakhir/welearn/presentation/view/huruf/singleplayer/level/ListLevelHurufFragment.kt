package com.tugasakhir.welearn.presentation.view.huruf.singleplayer.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentListLevelHurufBinding
import com.tugasakhir.welearn.presentation.presenter.singleplayer.LevelSoalPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListLevelHurufFragment : Fragment() {

    private var _binding: FragmentListLevelHurufBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LevelSoalPresenter by viewModel()
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
            view.findNavController().navigate(ListLevelHurufFragmentDirections.actionListLevelHurufNavToModeHurufNav())
        }

        sessionManager = activity?.let { SharedPreference(it) }!!
        showGridHuruf()
    }

    private fun showGridHuruf(){
        val hurufAdapter = ListLevelHurufAdapter()
        binding.progressLevelHuruf.visibility = View.VISIBLE

        hurufAdapter.onItemClick = {
            sessionManager.deleteIDLevel()
            val toSoalHuruf = ListLevelHurufFragmentDirections.toSoalHuruf()
            toSoalHuruf.idLevel = it.idLevel
            sessionManager.saveIDLevel(it.idLevel)
            view?.findNavController()?.navigate(toSoalHuruf)
        }

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.getLevelSoal(1, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        hurufAdapter.setData(it)
                        binding.progressLevelHuruf.visibility = View.GONE
                    }
            }
        }

        with(binding.rvLevelHuruf) {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(false)
            adapter = hurufAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}