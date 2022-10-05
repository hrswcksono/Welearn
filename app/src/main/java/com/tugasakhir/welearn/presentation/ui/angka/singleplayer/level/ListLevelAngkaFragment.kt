package com.tugasakhir.welearn.presentation.ui.angka.singleplayer.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tugasakhir.welearn.core.data.Resource
import com.tugasakhir.welearn.databinding.FragmentListLevelAngkaBinding
import com.tugasakhir.welearn.presentation.presenter.singleplayer.LevelSoalPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListLevelAngkaFragment : Fragment() {

    private var _binding: FragmentListLevelAngkaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LevelSoalPresenter by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListLevelAngkaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listAngkaBack.setOnClickListener {
            view.findNavController().navigate(ListLevelAngkaFragmentDirections.backModeAngka())
        }

        showGridAngka()
    }

    private fun showGridAngka() {
        val angkaAdapter = ListLevelAngkaAdapter()
        binding.progressLevelAngka.visibility = View.VISIBLE

        angkaAdapter.onItemClick = {
            val toSoalAngka = ListLevelAngkaFragmentDirections.toSoalAngka()
            toSoalAngka.idLevel = it.idLevel
            view?.findNavController()?.navigate(toSoalAngka)
        }

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.getLevelSoal(2)
                    .collectLatest { level ->
                        when(level) {
                            is Resource.Loading -> binding.progressLevelAngka.visibility = View.VISIBLE
                            is Resource.Success -> {
                                binding.progressLevelAngka.visibility = View.GONE
                                angkaAdapter.setData(level.data)
                            }
                            is Resource.Error -> {
                                binding.progressLevelAngka.visibility = View.GONE
                            }
                        }
                    }
            }
        }

//        angka_adapter.setData(LevelData.listLevel)
        with(binding.rvLevelAngka) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(false)
            adapter = angkaAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}