package com.tugasakhir.welearn.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_ANGKA
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentHomeBinding
import com.tugasakhir.welearn.presentation.ui.auth.login.LoginActivity
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreActivity
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
        callback.isEnabled
    }

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

        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_JOIN_ANGKA)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_GENERAL)

        binding.btnLogout.setOnClickListener {
            dialogLoOut()
        }

        binding.btnHighscore.setOnClickListener {
            startActivity(Intent(activity, ScoreActivity::class.java))
        }

    }

    private fun logout(token: String){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.logoutUser(token).collectLatest {
                    if (it == "Successfully logged out"){
                        logoutSuccess()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dialogLoOut(){
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

    private fun logoutSuccess(){
        CustomDialogBox.onlyTitle(
            requireContext(),
            SweetAlertDialog.SUCCESS_TYPE,
            "Berhasil keluar"
        ) {
            startActivity(Intent(activity, LoginActivity::class.java))
            sessionManager.deleteToken()
        }
    }
}