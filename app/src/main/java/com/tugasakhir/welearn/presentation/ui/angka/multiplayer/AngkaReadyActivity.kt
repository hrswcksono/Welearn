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
import com.tugasakhir.welearn.domain.model.PushNotificationStart
import com.tugasakhir.welearn.domain.model.StartGame
import com.tugasakhir.welearn.presentation.viewmodel.multiplayer.PushNotificationStartViewModel
import com.tugasakhir.welearn.presentation.viewmodel.multiplayer.PushNotificationViewModel
import com.tugasakhir.welearn.presentation.ui.angka.canvas.SoalAngkaByIDViewModel
import com.tugasakhir.welearn.presentation.viewmodel.multiplayer.JoinGameViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel


class AngkaReadyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaReadyBinding
    private val viewModel: PushNotificationViewModel by viewModel()
    private val viewModelSoal: SoalAngkaByIDViewModel by viewModel()
    private val viewModelGame: PushNotificationStartViewModel by viewModel()
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
        sessionManager = SharedPreference(this)

        binding.backToHome.setOnClickListener {
            startActivity(Intent(this@AngkaReadyActivity, MainActivity::class.java))
        }

        val idGame = intent.getStringExtra(ID_GAME)

        binding.btnAngkaReady.setOnClickListener {
//            Toast.makeText(this, idGame.toString(), Toast.LENGTH_SHORT).show()
            joinGame(idGame.toString())
//            ready()
        }

    }

    private fun ready() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModelGame.pushNotification(
                    PushNotificationStart(
                        StartGame(
                            "Perhatian...!",
                            "${sessionManager.fetchName()} telah bergabung!",
                            "",
                            "0",
                            0,
                            "gabung_angka"
                        ),
                        TOPIC_JOIN_ANGKA,
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
//        Toast.makeText(this@AngkaReadyActivity, "hfjafjkshdfs", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                joinGameViewModel.joinGame(id_game, sessionManager.fetchAuthToken().toString())
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