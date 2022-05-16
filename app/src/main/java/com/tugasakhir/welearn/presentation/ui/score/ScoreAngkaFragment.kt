package com.tugasakhir.welearn.presentation.ui.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentScoreAngkaBinding
import com.tugasakhir.welearn.presentation.ui.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel


class ScoreAngkaFragment : Fragment() {

    private var _binding: FragmentScoreAngkaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScoreAngkaViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

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

        sessionManager = SharedPreference(requireContext())

        showScore()
    }

    private fun showScore(){
        val scoreAngkaAdapter = ScoreAngkaAdapter()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.highScoreAngka(sessionManager.fetchAuthToken().toString()).collectLatest {
                    scoreAngkaAdapter.setData(it)
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