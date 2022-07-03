package com.tugasakhir.welearn.presentation.ui.angka.multiplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_ANGKA
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityAngkaReadyBinding
import com.tugasakhir.welearn.domain.model.NotificationData
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.presentation.presenter.multiplayer.JoinGameViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel


class AngkaReadyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaReadyBinding
//    private val viewModelGame: PushNotificationStartViewModel by viewModel()
    private val viewModelGame: PushNotificationViewModel by viewModel()
    private val joinGameViewModel: JoinGameViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    companion object{
        const val ID_GAME = "id_game"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaReadyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.backToHome.setOnClickListener {
            startActivity(Intent(this@AngkaReadyActivity, MainActivity::class.java))
        }

        sessionManager = SharedPreference(this)

        val idGame = intent.getStringExtra(ID_GAME)

        binding.btnAngkaReady.setOnClickListener {
            joinGame(idGame.toString())
            ready()
        }

    }

    private fun ready() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
//                viewModelGame.pushNotification(
//                    PushNotificationStart(
//                        StartGame(
//                            "Perhatian...!",
//                            "${sessionManager.fetchName()} telah bergabung!",
//                            "",
//                            "0",
//                            0,
//                            "gabung_angka"
//                        ),
//                        TOPIC_JOIN_ANGKA,
//                        "high"
//                    )
//                ).collectLatest {
//                    FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_GENERAL)
//                FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_ANGKA)
//                FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_ANGKA).addOnSuccessListener {
//                    dialogBox()
//                }
//                }
                viewModelGame.pushNotification(
                    PushNotification(
                        NotificationData(
                            "Perhatian...!",
                            "${sessionManager.fetchName()} telah bergabung!",
                            "",
                            "",
                            0,
                            "gabung_angka"
                        ), TOPIC_JOIN_ANGKA,
                        "high"
                    )
                ).collectLatest {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_GENERAL)
                    FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_ANGKA)
                    FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_ANGKA).addOnSuccessListener {
                        dialogBox()
                    }
                }
            }
        }
    }

    private fun joinGame(id_game: String) {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                joinGameViewModel.joinGame(id_game)
                    .collectLatest {
//                        Toast.makeText()
                        Toast.makeText(this@AngkaReadyActivity, it, Toast.LENGTH_SHORT).show()
                        if (it == "Berhasil Join") {
                            ready()
                        }
                    }
            }
        }
    }

    private fun dialogBox() {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Berhasil begabung...!")
            .setContentText("Harap menunggu pemain yang lain")
            .show()
    }

    override fun onBackPressed() {
        return
    }
}