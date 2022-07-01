package com.tugasakhir.welearn.presentation.ui.angka.multiplayer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_ANGKA
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityMatchAngkaBinding
import com.tugasakhir.welearn.domain.model.*
import com.tugasakhir.welearn.presentation.presenter.multiplayer.MakeRoomViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationStartViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.RandomLevelAngkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel


class MatchAngkaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchAngkaBinding
    private val viewModel: PushNotificationViewModel by viewModel()
    private val viewModelRandom: RandomLevelAngkaViewModel by viewModel()
    private val viewModelGame: PushNotificationStartViewModel by viewModel()
    private val makeRoomViewModel: MakeRoomViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchAngkaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_GENERAL)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_ANGKA)

        binding.btnBackMatchAngka.setOnClickListener {
            onBackPressed()
        }

        binding.btnAngkaAcak.setOnClickListener {
            makeRoom()
        }
    }

    private fun findPlayer(level: String, id_game : String) {
        binding.btnFindAngka.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModel.pushNotification(
                        PushNotification(
                            NotificationData(
                                "sessionManager.fetchName().toString()} mengajak anda bertanding Angka level $level!"
                                ,"Siapa yang ingin ikut?"
                                ,"angka",
                                id_game
                            ),
                            TOPIC_GENERAL,
                            "high"
                        )
                    ).collectLatest {  }
                }
            }
        }
    }

    private fun randomSoal(id_game: String){
        val inputLevel = binding.tfLevelAngka.text.toString()
        if (inputLevel.isNotEmpty()) {
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelRandom.randomSoalAngkaByLevel(
                        inputLevel.toInt()
                    ).collectLatest {
                        if (it.isNotEmpty()){
                            startMatch(it, inputLevel.toInt())
                            findPlayer(it, id_game)
                            dialogBox()
                        }
                    }
                }
            }
        }
    }

    private fun startMatch(idSoal: String, idLevel: Int) {
        binding.btnStartAngka.setOnClickListener{
                lifecycleScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.Main) {
                        viewModelGame.pushNotification(
                            PushNotificationStart(
                                StartGame(
                                    "Pertandingan telah dimulai",
                                    "Selamat mengerjakan !",
                                    "startangka",
                                    idSoal,
                                    idLevel,
                                    ""
                                ),
                                TOPIC_JOIN_ANGKA,
                                "high"
                            )
                        ).collectLatest {  }
                    }
                }
        }
    }

    private fun makeRoom(){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                makeRoomViewModel.makeRoom(2)
                    .collectLatest {
                        Toast.makeText(this@MatchAngkaActivity, it, Toast.LENGTH_SHORT).show()
                        if(it != "Gagal buat room"){
                            randomSoal(it)
                        }
                    }
            }
        }
    }

    private fun dialogBox() {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Berhasil")
            .setContentText("Mendapatkan Soal Angka")
            .show()
    }


}