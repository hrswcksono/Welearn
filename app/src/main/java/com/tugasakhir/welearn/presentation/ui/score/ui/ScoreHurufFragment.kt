package com.tugasakhir.welearn.presentation.ui.score.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentScoreHurufBinding
import com.tugasakhir.welearn.presentation.ui.score.adapter.ScoreHurufAdapter
import com.tugasakhir.welearn.presentation.presenter.score.ScoreHurufViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel


class ScoreHurufFragment : Fragment() {

    private var _binding: FragmentScoreHurufBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScoreHurufViewModel by viewModel()

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
    }

    private fun showScore() {
        binding.progressBarSHuruf.visibility = View.VISIBLE
        val scoreHurufAdapter = ScoreHurufAdapter()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.highScoreHuruf().collectLatest {
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
}