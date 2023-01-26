package com.tugasakhir.welearn.presentation.view.score.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentScoreHurufBinding
import com.tugasakhir.welearn.presentation.presenter.singleplayer.HighScorePresenter
import com.tugasakhir.welearn.presentation.view.score.adapter.ScoreHurufAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class ScoreHurufFragment : Fragment() {

    private var _binding: FragmentScoreHurufBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HighScorePresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScoreHurufBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showScore()
        header(View.INVISIBLE)
    }

    private fun showScore() {
        binding.progressBarSHuruf.visibility = View.VISIBLE
        val scoreHurufAdapter = ScoreHurufAdapter()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.highScore(1, sessionManager.fetchAuthToken()!!).collectLatest {
                    header(View.VISIBLE)
                    scoreHurufAdapter.setData(it)
                    binding.progressBarSHuruf.visibility = View.INVISIBLE
                }
            }
        }

        with(binding.rvScoreHuruf) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = scoreHurufAdapter
        }
    }

    private fun header(vis: Int){
        binding.tvhNo.visibility = vis
        binding.tvhUsername.visibility = vis
        binding.tvhSkor.visibility = vis
    }
}