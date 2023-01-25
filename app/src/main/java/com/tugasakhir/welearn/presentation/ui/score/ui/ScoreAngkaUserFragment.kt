package com.tugasakhir.welearn.presentation.ui.score.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentScoreAngkaUserBinding
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ScoreUserPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreAngkaUserFragment : Fragment() {

    private var _binding: FragmentScoreAngkaUserBinding ?= null
    private val binding get() = _binding!!
    private val viewModel: ScoreUserPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScoreAngkaUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SharedPreference(it) }!!

        show()

        binding.backDaftarSoalAngka.setOnClickListener {
            val backDaftarSoal = ScoreAngkaUserFragmentDirections.backToListSoalAngka()
            backDaftarSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backDaftarSoal)
        }

        binding.btnHome.setOnClickListener {
            view?.findNavController()?.navigate(ScoreAngkaUserFragmentDirections.backToHomeFromScoreAngka())
        }
    }

    private fun show() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.userScore(2).collectLatest {
                    binding.tvScoreUserAngka.text = it.score.toString()
                }
            }
        }
    }

}