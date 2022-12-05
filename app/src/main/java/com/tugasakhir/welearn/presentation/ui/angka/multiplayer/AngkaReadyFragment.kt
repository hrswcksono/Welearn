package com.tugasakhir.welearn.presentation.ui.angka.multiplayer

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
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentAngkaReadyBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.presentation.presenter.multiplayer.JoinGamePresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AngkaReadyFragment : Fragment() {

    private var _binding: FragmentAngkaReadyBinding ?= null
    private val binding get() = _binding!!
    private val viewModelGame: PushNotificationPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAngkaReadyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToHome.setOnClickListener {
            view?.findNavController()?.navigate(AngkaReadyFragmentDirections.backHomeAngkaReady())
        }

        sessionManager = SharedPreference(activity!!)

        val idGame = arguments?.getString("idGame")

        binding.btnAngkaReady.setOnClickListener {
            joinGame(idGame.toString())
            ready()
        }
    }

    private fun ready() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModelGame.pushNotification(
                    PushNotification(
                        NotificationData(
                            "Perhatian...!",
                            "${sessionManager.fetchName()} telah bergabung!",
                            "",
                            "",
                            0,
                            "gabung_angka"
                        ), Constants.TOPIC_JOIN_ANGKA,
                        "high"
                    )
                ).collectLatest {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_GENERAL)
                    FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_JOIN_ANGKA)
                    FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_JOIN_ANGKA).addOnSuccessListener {
                        dialogBox()
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