package com.tugasakhir.welearn.presentation.ui.angka.multiplayer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_ANGKA
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityMatchAngkaBinding
import com.tugasakhir.welearn.domain.entity.*
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchAngkaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchAngkaBinding
    private val viewModel: PushNotificationPresenter by viewModel()
    private val viewModelRandom: RandomIDSoalMultiPresenter by viewModel()
    private val makeRoomPresenter: MakeRoomPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchAngkaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sessionManager = SharedPreference(this)

        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_GENERAL)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_ANGKA)

        binding.btnBackMatchAngka.setOnClickListener {
            onBackPressed()
        }
        choseeLevel()
    }

    private fun choseeLevel(){
        eraseCheckLevel()

        binding.pgCariPlayerAngka.visibility = View.INVISIBLE
        binding.pgAngkaAcak.visibility = View.INVISIBLE
        binding.cekCariPlayerAngka.visibility = View.INVISIBLE
        binding.cekAcakAngka.visibility = View.INVISIBLE

        var inputLevel = 0
        binding.bhLevel0.setOnClickListener {
            eraseCheckLevel()
            binding.alevel0.visibility = View.VISIBLE
            inputLevel = 0
        }
        binding.bhLevel1.setOnClickListener {
            eraseCheckLevel()
            binding.alevel1.visibility = View.VISIBLE
            inputLevel = 1
        }
        binding.bhLevel2.setOnClickListener {
            eraseCheckLevel()
            binding.alevel2.visibility = View.VISIBLE
            inputLevel = 2
        }
        binding.bhLevel3.setOnClickListener {
            eraseCheckLevel()
            binding.alevel3.visibility = View.VISIBLE
            inputLevel = 3
        }
        binding.bhLevel4.setOnClickListener {
            eraseCheckLevel()
            binding.alevel4.visibility = View.VISIBLE
            inputLevel = 4
        }
        randomSoal(4)
    }

    private fun findPlayer(level: String, id_game : String) {
        binding.btnFindAngka.setOnClickListener {
            binding.pgCariPlayerAngka.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModel.pushNotification(
                        PushNotification(
                            NotificationData(
                                "${sessionManager.fetchName().toString()} mengajak anda bertanding Angka!"
                                ,"Siapa yang ingin ikut?"
                                ,"angka",
                                "",
                                0,
                                id_game
                            ),
                            TOPIC_GENERAL,
                            "high"
                        )
                    ).collectLatest {
                        binding.pgCariPlayerAngka.visibility = View.INVISIBLE
                        binding.cekCariPlayerAngka.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun eraseCheckLevel(){
        binding.alevel0.visibility = View.INVISIBLE
        binding.alevel1.visibility = View.INVISIBLE
        binding.alevel2.visibility = View.INVISIBLE
        binding.alevel3.visibility = View.INVISIBLE
        binding.alevel4.visibility = View.INVISIBLE
    }

    private fun randomSoal(inputLevel: Int){
        binding.btnAngkaAcak.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelRandom.randomIDSoalMultiByLevel(2,
                        inputLevel
                    ).collectLatest {
                        if (it.isNotEmpty()){
                            binding.pgAngkaAcak.visibility = View.INVISIBLE
                            binding.cekAcakAngka.visibility = View.VISIBLE
                            CustomDialogBox.notifOnly(this@MatchAngkaActivity, "Berhasil Mendapatkan Soal")
                            startMatch(it, inputLevel)
                            findPlayer(it, inputLevel.toString())
//                            dialogBox()
                        }
                    }
                }
            }
        }
    }

    private fun startMatch(idSoal: String, idLevel: Int) {
        binding.btnStartAngka.setOnClickListener{
                makeRoom(idSoal, idLevel)
        }
    }

    private fun makeRoom(idSoal: String, idLevel: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                makeRoomPresenter.makeRoom(2)
                    .collectLatest {
                        lifecycleScope.launch(Dispatchers.Default) {
                            withContext(Dispatchers.Main) {
                                viewModel.pushNotification(
                                    PushNotification(
                                        NotificationData(
                                            "Pertandingan telah dimulai",
                                            "Selamat mengerjakan !",
                                            "startangka",
                                            idSoal,
                                            idLevel,
                                            it
                                        ),
                                        TOPIC_JOIN_ANGKA,
                                        "high"
                                    )
                                ).collectLatest {  }
                            }
                        }
                    }
            }
        }
    }
}