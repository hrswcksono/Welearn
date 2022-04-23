package com.tugasakhir.welearn.presentation.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentProfileBinding
import com.tugasakhir.welearn.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SharedPreference(requireContext())

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.detailUser(sessionManager.fetchAuthToken().toString()).collectLatest {
                    showData(it)
                }
            }
        }
    }

    private fun showData(user: User) {
        binding.tvEmail.text = user.email
        binding.tvNama.text = user.username
        binding.tvGender.text = user.jenis_kelamin
        binding.tvScoreHuruf.text = user.score
        binding.tvScoreAngka.text = user.angka
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}