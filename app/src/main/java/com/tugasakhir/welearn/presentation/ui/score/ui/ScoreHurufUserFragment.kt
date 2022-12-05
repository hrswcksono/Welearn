package com.tugasakhir.welearn.presentation.ui.score.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.FragmentScoreHurufUserBinding
import com.tugasakhir.welearn.domain.entity.ScoreEntity
import com.tugasakhir.welearn.presentation.presenter.score.ScoreUserPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreHurufUserFragment : Fragment() {

    private var _binding: FragmentScoreHurufUserBinding ?= null
    private val binding get() = _binding!!
    private val viewModel: ScoreUserPresenter by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScoreHurufUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        show()

        binding.btnBackDaftarSoalHuruf.setOnClickListener {
            view?.findNavController()?.navigate(ScoreHurufUserFragmentDirections.backToListSoalHuruf())
        }

        binding.btnHome.setOnClickListener {
            view?.findNavController()?.navigate(ScoreHurufUserFragmentDirections.backToHomeFromScoreHuruf())
        }
    }

    private fun show() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.userScore(1).collectLatest {
                    showData(it)
                }
            }
        }
    }

    private fun showData(data: ScoreEntity) {
        binding.tvScoreUserHuruf.text = data.score.toString()
    }
}