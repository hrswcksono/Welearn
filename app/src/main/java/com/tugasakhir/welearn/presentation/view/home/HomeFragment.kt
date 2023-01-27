package com.tugasakhir.welearn.presentation.view.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_ANGKA
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_HURUF
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentHomeBinding
import com.tugasakhir.welearn.presentation.view.auth.LoginActivity
import com.tugasakhir.welearn.presentation.view.score.ui.ScoreActivity
import com.tugasakhir.welearn.presentation.presenter.user.LogoutPresenter
import com.tugasakhir.welearn.presentation.view.score.ui.JoinedGameActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LogoutPresenter by viewModel()
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

        binding.progressbarHome.visibility = View.INVISIBLE

        binding.btnAngka.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.toModeAngka())
        }

        binding.btnHuruf.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.toModeHuruf())
        }

        sessionManager = activity?.let { SharedPreference(it) }!!

        subscribeFCM()

        binding.btnLogout.setOnClickListener {
            dialogLoOut()
        }

        binding.btnHighscore.setOnClickListener {
            dialogScore()
        }

    }

    private fun logout(){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.logoutUser(sessionManager.fetchAuthToken()!!).collectLatest {
                    if (it == "Successfully logged out"){
                        binding.progressbarHome.visibility = View.INVISIBLE
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
        CustomDialogBox.withCancel(
            requireContext(),
            SweetAlertDialog.WARNING_TYPE,
            "Apakah yakin?",
            "Keluar dari akun ini!",
            "Logout!",
        ) {
            binding.progressbarHome.visibility = View.VISIBLE
            logout()
        }
    }

    private fun subscribeFCM(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_JOIN_ANGKA)
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_JOIN_HURUF)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_GENERAL)
    }

    private fun logoutSuccess(){
        CustomDialogBox.notifOnly(requireActivity(), "Berhasil Logout")
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_GENERAL)
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
//        sessionManager.deleteToken()
    }

    private fun dialogScore(){
        val flatDialog = FlatDialog(requireContext())
        flatDialog
            .setTitle("Lihat Score")
            .setTitleColor(Color.BLACK)
            .setFirstButtonText("Single Player")
            .setFirstButtonColor(Color.parseColor("#a6e474"))
            .setFirstButtonTextColor(Color.BLACK)
            .setSecondButtonText("Multi Player")
            .setSecondButtonColor(Color.parseColor("#e0f84a"))
            .setSecondButtonTextColor(Color.BLACK)
            .setBackgroundColor(Color.parseColor("#e3ffdf"))
            .isCancelable(true)
            .withFirstButtonListner {
                flatDialog.dismiss()
                startActivity(Intent(activity, ScoreActivity::class.java))
            }
            .withSecondButtonListner {
                flatDialog.dismiss()
                startActivity(Intent(activity, JoinedGameActivity::class.java))
            }
            .show()
    }
}