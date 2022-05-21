package com.tugasakhir.welearn.presentation.ui.angka.multiplayer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.core.utils.FirebaseService
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityMatchAngkaBinding
import com.tugasakhir.welearn.domain.model.NotificationData
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.presentation.ui.PushNotificationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

const val TOPIC = "/topics/myTopic"
class MatchAngkaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchAngkaBinding
    private val viewModel: PushNotificationViewModel by viewModel()
    private val viewModelRandom: RandomLevelAngkaViewModel by viewModel()

    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchAngkaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sessionManager = SharedPreference(this)

        binding.btnAngkaAcak.setOnClickListener {
            val inputLevel = binding.tfLevelAngka.text.toString()
            if (inputLevel.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.Main) {
                        viewModelRandom.randomSoalAngkaByLevel(
                            inputLevel.toInt(),
                            sessionManager.fetchAuthToken().toString()
                        ).collectLatest {
                            if (it.id_soal.toString().isNotEmpty()){
                                dialogBox()
                            }
                        }
                    }
                }
            }
        }

        binding.btnFindAngka.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModel.pushNotification(
                        PushNotification(
                            NotificationData(
                                "Pertandingan MultiPlayer Segera Dimuali!"
                                ,"Siapa yang ingin ikut?"
                            ),
                            "/topics/myTopics"
                        )
                    ).collectLatest {  }
                }
            }
        }

        binding.btnStartAngka.setOnClickListener {

        }
    }

    private fun dialogBox() {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Berhasil")
            .setContentText("Mendapatkan Soal Angka")
            .show()
    }
}