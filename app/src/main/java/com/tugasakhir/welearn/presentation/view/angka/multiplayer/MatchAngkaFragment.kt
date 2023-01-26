package com.tugasakhir.welearn.presentation.view.angka.multiplayer

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
import com.tugasakhir.welearn.databinding.FragmentMatchAngkaBinding
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

class MatchAngkaFragment : Fragment() {

    private var _binding: FragmentMatchAngkaBinding ?= null
    private val binding get() = _binding!!
    private val viewModel: PushNotificationPresenter by viewModel()
    private val viewModelRandom: RandomIDSoalMultiPresenter by viewModel()
    private val makeRoomPresenter: MakeRoomPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchAngkaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SharedPreference(activity!!)

        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_GENERAL)
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_JOIN_ANGKA)

        binding.btnBackMatchAngka.setOnClickListener {
            view?.findNavController()?.navigateUp()
        }
        choseeLevel()
    }

    private fun choseeLevel(){
//        eraseCheckLevel()
//
//        binding.pgCariPlayerAngka.visibility = View.INVISIBLE
//        binding.pgAngkaAcak.visibility = View.INVISIBLE
//        binding.cekCariPlayerAngka.visibility = View.INVISIBLE
//        binding.cekAcakAngka.visibility = View.INVISIBLE
//
//        var inputLevel: Int
//        binding.bhLevel0.setOnClickListener {
//            eraseCheckLevel()
//            binding.alevel0.visibility = View.VISIBLE
//            inputLevel = 0
//            randomSoal(inputLevel)
//        }
//        binding.bhLevel1.setOnClickListener {
//            eraseCheckLevel()
//            binding.alevel1.visibility = View.VISIBLE
//            inputLevel = 1
//            randomSoal(inputLevel)
//        }
//        binding.bhLevel2.setOnClickListener {
//            eraseCheckLevel()
//            binding.alevel2.visibility = View.VISIBLE
//            inputLevel = 2
//            randomSoal(inputLevel)
//        }
//        binding.bhLevel3.setOnClickListener {
//            eraseCheckLevel()
//            binding.alevel3.visibility = View.VISIBLE
//            inputLevel = 3
//            randomSoal(inputLevel)
//        }
//        binding.bhLevel4.setOnClickListener {
////            eraseCheckLevel()
////            binding.alevel4.visibility = View.VISIBLE
//            inputLevel = 4
//            randomSoal(inputLevel)
//        }
    }

    private fun findPlayer(level: String, id_game : String) {
        binding.btnFindAngka.setOnClickListener {
//            binding.pgCariPlayerAngka.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModel.pushNotification(
                        PushNotification(
                            NotificationData(
                                "${sessionManager.fetchName().toString()} mengajak anda bertanding Angka level $level"
                                ,"Siapa yang ingin ikut?"
                                ,"angka",
                                "",
                                0,
                                id_game
                            ),
                            Constants.TOPIC_GENERAL,
                            "high"
                        )
                    ).collectLatest {
//                        binding.pgCariPlayerAngka.visibility = View.INVISIBLE
//                        binding.cekCariPlayerAngka.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

//    private fun eraseCheckLevel(){
//        binding.alevel0.visibility = View.INVISIBLE
//        binding.alevel1.visibility = View.INVISIBLE
//        binding.alevel2.visibility = View.INVISIBLE
//        binding.alevel3.visibility = View.INVISIBLE
//        binding.alevel4.visibility = View.INVISIBLE
//    }

    private fun randomSoal(inputLevel: Int){
        binding.btnAngkaAcak.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelRandom.randomIDSoalMultiByLevel(2,
                        inputLevel, sessionManager.fetchAuthToken()!!
                    ).collectLatest {
                        if (it.id_soal.isNotEmpty()){
//                            binding.pgAngkaAcak.visibility = View.INVISIBLE
//                            binding.cekAcakAngka.visibility = View.VISIBLE
                            CustomDialogBox.dialogSoalMulti(context!!)
                            startMatch(it.id_soal, inputLevel)
                            findPlayer(inputLevel.toString(), inputLevel.toString())
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
                makeRoomPresenter.makeRoom(2, idLevel, sessionManager.fetchAuthToken()!!)
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
                                        Constants.TOPIC_JOIN_ANGKA,
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