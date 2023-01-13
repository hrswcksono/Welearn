package com.tugasakhir.welearn.presentation.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.databinding.FragmentProfileBinding
import com.tugasakhir.welearn.domain.entity.ProfileEntity
import com.tugasakhir.welearn.presentation.presenter.user.ProfilePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfilePresenter by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.detailUser().collectLatest {
                    showData(it)
                    binding.progressBar2.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun showData(user: ProfileEntity) {
        binding.tvEmail.text = user.email
        binding.tvNama.text = user.username
        binding.tvGender.text = user.jenisKelamin
        binding.tvScoreHuruf.text = user.score
        binding.tvScoreAngka.text = user.angka
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}