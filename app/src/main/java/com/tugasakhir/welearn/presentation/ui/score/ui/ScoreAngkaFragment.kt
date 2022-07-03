package com.tugasakhir.welearn.presentation.ui.score.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugasakhir.welearn.databinding.FragmentScoreAngkaBinding
import com.tugasakhir.welearn.presentation.ui.score.adapter.ScoreAngkaAdapter
import com.tugasakhir.welearn.presentation.presenter.score.ScoreAngkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel


class ScoreAngkaFragment : Fragment() {

    private var _binding: FragmentScoreAngkaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScoreAngkaViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScoreAngkaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showScore()
    }

    private fun showScore(){
        binding.progressBarSAngka.visibility = View.VISIBLE
        val scoreAngkaAdapter = ScoreAngkaAdapter()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.highScoreAngka().collectLatest {
                    scoreAngkaAdapter.setData(it)
                    binding.progressBarSAngka.visibility = View.INVISIBLE
                }
            }
        }

        with(binding.rvScoreAngka) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = scoreAngkaAdapter
        }
    }
}