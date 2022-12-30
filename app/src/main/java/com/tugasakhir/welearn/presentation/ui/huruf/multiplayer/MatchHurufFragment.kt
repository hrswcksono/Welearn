package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentMatchHurufBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.presentation.presenter.multiplayer.MakeRoomPresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationPresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.RandomIDSoalMultiPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchHurufFragment : Fragment() {

    private var _binding: FragmentMatchHurufBinding ?= null
    private val binding get() = _binding!!
    private val viewModel: PushNotificationPresenter by viewModel()
    private val viewModelRandom: RandomIDSoalMultiPresenter by viewModel()
    private val makeRoomPresenter: MakeRoomPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchHurufBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView4.setOnClickListener {
            view?.findNavController()?.navigateUp()
        }

        sessionManager = SharedPreference(activity!!)

        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_GENERAL)
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_JOIN_HURUF)

        choseeLevel()
    }

    private fun choseeLevel(){
        eraseCheckLevel()

        binding.pgCariPlayerHuruf.visibility = View.INVISIBLE
        binding.pgHurufAcak.visibility = View.INVISIBLE
        binding.cekAcakHuruf.visibility = View.INVISIBLE
        binding.cekCariPlayerHuruf.visibility = View.INVISIBLE

        var inputLevel: Int
        binding.baLevel0.setOnClickListener {
            eraseCheckLevel()
            binding.hlevel0.visibility = View.VISIBLE
            inputLevel = 0
            randomSoal(inputLevel)
        }
        binding.baLevel1.setOnClickListener {
            eraseCheckLevel()
            binding.hlevel1.visibility = View.VISIBLE
            inputLevel = 1
            randomSoal(inputLevel)
        }
        binding.baLevel2.setOnClickListener {
            eraseCheckLevel()
            binding.hlevel2.visibility = View.VISIBLE
            inputLevel = 2
            randomSoal(inputLevel)
        }
        binding.baLevel3.setOnClickListener {
            eraseCheckLevel()
            binding.hlevel3.visibility = View.VISIBLE
            inputLevel = 3
            randomSoal(inputLevel)
        }
    }

    private fun randomSoal(inputLevel: Int) {
        binding.btnHurufAcak.setOnClickListener {
            binding.pgHurufAcak.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelRandom.randomIDSoalMultiByLevel(1,
                        inputLevel
                    ).collectLatest {
                        if (it.id_soal.isNotEmpty()){
                            binding.pgHurufAcak.visibility = View.INVISIBLE
                            binding.cekAcakHuruf.visibility = View.VISIBLE
                            activity?.let { it1 ->
                                CustomDialogBox.notifOnly(
                                    it1,
                                    "Berhasil Mendapatkan Soal"
                                )
                            }
                            startMatch(it.id_soal, inputLevel)
                            findPlayer(it.id_soal)
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
                                "${sessionManager.fetchName().toString()} mengajak anda bertanding Angka level ${level}!"
                                ,"Siapa yang ingin ikut?"
                                ,"huruf",
                                "",
                                0,
                                ""
                            ),
                            Constants.TOPIC_GENERAL,
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
                makeRoomPresenter.makeRoom(1, idLevel).collectLatest {
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
                                    Constants.TOPIC_JOIN_HURUF,
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