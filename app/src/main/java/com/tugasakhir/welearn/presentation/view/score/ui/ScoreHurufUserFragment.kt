package com.tugasakhir.welearn.presentation.view.score.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentScoreHurufUserBinding
import com.tugasakhir.welearn.domain.entity.Score
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ScoreUserPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreHurufUserFragment : Fragment() {

    private var _binding: FragmentScoreHurufUserBinding ?= null
    private val binding get() = _binding!!
    private val viewModel: ScoreUserPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScoreHurufUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SharedPreference(it) }!!

        show()

        binding.btnBackDaftarSoalHuruf.setOnClickListener {
            val backDaftarSoal = ScoreHurufUserFragmentDirections.backToListSoalHuruf()
            backDaftarSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backDaftarSoal)
        }

        binding.btnHome.setOnClickListener {
            view?.findNavController()?.navigate(ScoreHurufUserFragmentDirections.backToHomeFromScoreHuruf())
        }
    }

    private fun show() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.userScore(1, sessionManager.fetchAuthToken()!!).collectLatest {
                    showData(it)
                }
            }
        }
    }

    private fun showData(data: Score) {
        binding.tvScoreUserHuruf.text = data.score.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}