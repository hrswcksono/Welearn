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
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.utils.ExitApp
import com.tugasakhir.welearn.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentHurufReadyBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.presentation.presenter.multiplayer.JoinGamePresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationPresenter
import com.tugasakhir.welearn.utils.CustomDialogBox
import com.tugasakhir.welearn.utils.Template
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

        binding.btnHurufReady.setOnClickListener {
            if (binding.tfIdRoomHuruf.text.toString().toIntOrNull() != null){
                joinGame(binding.tfIdRoomHuruf.text.toString().toInt())
            } else{
                SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Input salah...!")
                    .setContentText("Hanya boleh masukkan angka")
                    .show()
            }
        }
    }

    private fun ready(topic: String) {
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
                        Template.getTopic(topic),
                        "high"
                    )
                ).collectLatest {
                    ExitApp.topic = topic
                    FirebaseMessaging.getInstance().subscribeToTopic(Template.getTopic(topic))
                    FirebaseMessaging.getInstance().subscribeToTopic(Template.getTopic(topic)).addOnSuccessListener {
                        dialogBox()
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
                        when(it) {
                            is Resource.Success ->{
                                if (it.data!!.status == "Berhasil Join") {
                                    ready(binding.tfIdRoomHuruf.text.toString())
                                } else {
                                    SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Gagal begabung...!")
                                        .setContentText(it.data.status)
                                        .show()
                                }
                            }
                            is Resource.Loading ->{}
                            is Resource.Error ->{
                                CustomDialogBox.dialogNoInternet(context!!)
                            }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}