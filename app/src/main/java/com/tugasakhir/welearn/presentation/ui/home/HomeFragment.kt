package com.tugasakhir.welearn.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentHomeBinding
import com.tugasakhir.welearn.presentation.ui.angka.soal.ListSoalAngkaViewModel
import com.tugasakhir.welearn.presentation.ui.auth.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LogoutViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SharedPreference(requireContext())

        binding.btnAngka.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.toModeAngka())
        }
        binding.btnHuruf.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.toModeHuruf())
        }

        binding.btnLogout.setOnClickListener {
            SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Apakah yakin?")
                .setContentText("Keluar aplikasi ini!")
                .setConfirmText("Logout!")
                .setConfirmClickListener {
                        sDialog -> sDialog.dismissWithAnimation()
                        logout(sessionManager.fetchAuthToken().toString())
                }
                .setCancelButton(
                    "Batal"
                ) { sDialog -> sDialog.dismissWithAnimation() }
                .show()
        }
    }

    private fun logout(token: String){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.logoutUser(token).collectLatest {
                    if (it == "Successfully logged out"){
                        startActivity(Intent(activity, LoginActivity::class.java))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}