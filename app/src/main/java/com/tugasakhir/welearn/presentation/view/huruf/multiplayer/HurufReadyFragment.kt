package com.tugasakhir.welearn.presentation.view.huruf.multiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.ExitApp
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentHurufReadyBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.presentation.presenter.multiplayer.JoinGamePresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationPresenter
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

        binding.backToMultiHuruf.setOnClickListener {
            view?.findNavController()?.navigateUp()
        }

        sessionManager = SharedPreference(activity!!)

        val idRoom = arguments?.getString("idGame")

        binding.btnHurufReady.setOnClickListener {
            joinGame(idRoom!!.toInt())
        }
    }

    private fun ready(topic: String) {
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
                            topic,
                            "high"
                        )
                    ).collectLatest {
                        ExitApp.topic = topic
                        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener {
                            dialogBox()
                        }
                    }
                }
            }
        }
    }

    private fun joinGame(id_room: Int) {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                joinGamePresenter.joinGame(id_room, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        if (it.status == "Berhasil Join") {
                            ready(binding.tfIdRoomHuruf.text.toString())
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

    override fun onStop() {
        super.onStop()

    }
}