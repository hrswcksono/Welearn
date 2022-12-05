package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentHurufReadyBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.presentation.presenter.multiplayer.JoinGamePresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationPresenter
import com.tugasakhir.welearn.presentation.ui.angka.multiplayer.AngkaReadyActivity
import com.tugasakhir.welearn.presentation.ui.angka.multiplayer.AngkaReadyFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HurufReadyFragment : Fragment() {

    private var _binding: FragmentHurufReadyBinding ?= null
    private val binding get() = _binding!!
    private val viewModelGame: PushNotificationPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHurufReadyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToHome.setOnClickListener {
            view?.findNavController()?.navigate(HurufReadyFragmentDirections.backHomeHurufReady())
        }

        sessionManager = SharedPreference(activity!!)

        val idGame = arguments?.getString("idGame")

        binding.btnHurufReady.setOnClickListener {
            joinGame(idGame.toString())
            ready()
        }
    }

    private fun ready() {
        binding.btnHurufReady.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelGame.pushNotification(
                        PushNotification(
                            NotificationData(
                                "Perhatian...!",
                                "${sessionManager.fetchName()} telah bergabung!",
                                "",
                                "0",
                                0,
                                "gabung_huruf"
                            ),
                            Constants.TOPIC_JOIN_HURUF,
                            "high"
                        )
                    ).collectLatest {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_GENERAL)
                        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_JOIN_HURUF)
                        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_JOIN_HURUF).addOnSuccessListener {
                            dialogBox()
                        }
                    }
                }
            }
        }
    }

    private fun joinGame(id_game: String) {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                joinGamePresenter.joinGame(id_game)
                    .collectLatest {
//                        Toast.makeText()
                        Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                        if (it == "Berhasil Join") {
                            ready()
                        }
                    }
            }
        }
    }

    private fun dialogBox() {
        SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Berhasil begabung...!")
            .setContentText("Harap menunggu pemain yang lain")
            .show()
    }

}