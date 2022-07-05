package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_HURUF
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityMatchHurufBinding
import com.tugasakhir.welearn.domain.model.*
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class MatchHurufActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchHurufBinding
    private val viewModel: PushNotificationPresenter by viewModel()
    private val viewModelRandom: RandomIDSoalMultiPresenter by viewModel()
    private val makeRoomPresenter: MakeRoomPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imageView4.setOnClickListener {
            onBackPressed()
        }

        sessionManager = SharedPreference(this)

        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_GENERAL)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_HURUF)

        choseeLevel()
    }

    private fun choseeLevel(){
        eraseCheckLevel()

        binding.pgCariPlayerHuruf.visibility = View.INVISIBLE
        binding.pgHurufAcak.visibility = View.INVISIBLE
        binding.cekAcakHuruf.visibility = View.INVISIBLE
        binding.cekCariPlayerHuruf.visibility = View.INVISIBLE

        var inputLevel = 0
        binding.baLevel0.setOnClickListener {
            eraseCheckLevel()
            binding.hlevel0.visibility = View.VISIBLE
            inputLevel = 0
        }
        binding.baLevel1.setOnClickListener {
            eraseCheckLevel()
            binding.hlevel1.visibility = View.VISIBLE
            inputLevel = 1
        }
        binding.baLevel2.setOnClickListener {
            eraseCheckLevel()
            binding.hlevel2.visibility = View.VISIBLE
            inputLevel = 2
        }
        binding.baLevel3.setOnClickListener {
            eraseCheckLevel()
            binding.hlevel3.visibility = View.VISIBLE
            inputLevel = 3
        }
        randomSoal(inputLevel)
    }

    private fun randomSoal(inputLevel: Int) {
        binding.btnHurufAcak.setOnClickListener {
            binding.pgHurufAcak.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelRandom.randomIDSoalMultiByLevel(1,
                        inputLevel
                    ).collectLatest {
                        if (it.isNotEmpty()){
                            binding.pgHurufAcak.visibility = View.INVISIBLE
                            binding.cekAcakHuruf.visibility = View.VISIBLE
                            CustomDialogBox.notifOnly(this@MatchHurufActivity, "Berhasil Mendapatkan Soal")
                            startMatch(it, inputLevel)
                            findPlayer(it)
                        }
                    }
                }
            }
        }
    }

    private fun eraseCheckLevel(){
        binding.hlevel0.visibility = View.INVISIBLE
        binding.hlevel1.visibility = View.INVISIBLE
        binding.hlevel2.visibility = View.INVISIBLE
        binding.hlevel3.visibility = View.INVISIBLE
    }

    private fun findPlayer(level: String) {
        binding.btnFindHuruf.setOnClickListener {
            binding.pgCariPlayerHuruf.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModel.pushNotification(
                        PushNotification(
                            NotificationData(
                                "${sessionManager.fetchName().toString()} mengajak anda bertanding Angka level $level!"
                                ,"Siapa yang ingin ikut?"
                                ,"huruf",
                                "",
                                0,
                                ""
                            ),
                            TOPIC_GENERAL,
                            "high"
                        )
                    ).collectLatest {
                        binding.pgCariPlayerHuruf.visibility = View.INVISIBLE
                        binding.cekCariPlayerHuruf.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun startMatch(idSoal: String, idLevel: Int) {
        binding.btnStartHuruf.setOnClickListener{
            makeRoom(idSoal, idLevel)
        }
    }

    private fun makeRoom(idSoal: String, idLevel: Int){
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Default) {
                makeRoomPresenter.makeRoom(1).collectLatest {
                    lifecycleScope.launch(Dispatchers.Default) {
                        withContext(Dispatchers.Main) {
                            viewModel.pushNotification(
                                PushNotification(
                                    NotificationData(
                                        "Pertandingan telah dimulai",
                                        "Selamat mengerjakan !",
                                        "starthuruf",
                                        idSoal,
                                        idLevel,
                                        it
                                    ),
                                    TOPIC_JOIN_HURUF,
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