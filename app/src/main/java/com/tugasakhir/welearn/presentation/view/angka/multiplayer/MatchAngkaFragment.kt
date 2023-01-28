package com.tugasakhir.welearn.presentation.view.angka.multiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.FragmentMatchAngkaBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.presentation.presenter.multiplayer.MakeRoomPresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationPresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.RandomIDSoalMultiPresenter
import com.tugasakhir.welearn.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchAngkaFragment : Fragment() {

    private var _binding: FragmentMatchAngkaBinding ?= null
    private val binding get() = _binding!!
    private val pushNotif: PushNotificationPresenter by viewModel()
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

        binding.btnBackMatchAngka.setOnClickListener {
            view?.findNavController()?.navigateUp()
        }
        choseeLevel()
    }

    private fun choseeLevel(){
//        eraseCheckLevel()
//

        var inputLevel: Int
        binding.bhLevel0.setOnClickListener {
            refreshButton()
            binding.bhLevel0.setBackgroundResource(R.drawable.yellow_j)
            inputLevel = 0
            randomSoal(inputLevel)
        }
        binding.bhLevel1.setOnClickListener {
            refreshButton()
            binding.bhLevel1.setBackgroundResource(R.drawable.yellow_j)
            inputLevel = 1
            randomSoal(inputLevel)
        }
        binding.bhLevel2.setOnClickListener {
            refreshButton()
            binding.bhLevel2.setBackgroundResource(R.drawable.yellow_j)
            inputLevel = 2
            randomSoal(inputLevel)
        }
        binding.bhLevel3.setOnClickListener {
            refreshButton()
            binding.bhLevel3.setBackgroundResource(R.drawable.yellow_j)
            inputLevel = 3
            randomSoal(inputLevel)
        }
        binding.bhLevel4.setOnClickListener {
            refreshButton()
            binding.bhLevel4.setBackgroundResource(R.drawable.yellow_j)
            inputLevel = 4
            randomSoal(inputLevel)
        }
    }

    private fun refreshButton(){
        binding.bhLevel0.setBackgroundResource(R.drawable.bg_grid)
        binding.bhLevel1.setBackgroundResource(R.drawable.bg_grid)
        binding.bhLevel2.setBackgroundResource(R.drawable.bg_grid)
        binding.bhLevel3.setBackgroundResource(R.drawable.bg_grid)
        binding.bhLevel4.setBackgroundResource(R.drawable.bg_grid)
    }

    private fun randomSoal(inputLevel: Int){
        binding.btnAngkaAcak.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelRandom.randomIDSoalMultiByLevel(2,
                        inputLevel, sessionManager.fetchAuthToken()!!
                    ).collectLatest {
                        if (it.id_soal.isNotEmpty()){
                            CustomDialogBox.dialogSoalMulti(context!!)
                            makeRoom(inputLevel, it.id_soal)
                            binding.btnAngkaAcak.setBackgroundResource(R.drawable.yellow)
                        }
                    }
                }
            }
        }
    }

    private fun startMatch(idLevel: Int, idSoal: String, topic: String, id_game: String) {
        binding.btnStartAngka.setOnClickListener{
            binding.btnStartAngka.setBackgroundResource(R.drawable.yellow)
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    pushNotif.pushNotification(
                        PushNotification(
                            NotificationData(
                                "Pertandingan telah dimulai",
                                "Selamat mengerjakan !",
                                "startangka",
                                idSoal,
                                idLevel,
                                id_game
                            ),
                            Template.getTopic(topic),
                            "high"
                        )
                    ).collectLatest {  }
                }
            }
        }
    }

    private fun makeRoom(idLevel: Int, idSoal:String){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                makeRoomPresenter.makeRoom(2, idLevel, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        sessionManager.saveIDRoom(it.id_room)
                        ExitApp.topic = it.id_room.toString()
                        binding.tvIdRoomAngka.text = it.id_room.toString()
                        FirebaseMessaging.getInstance().subscribeToTopic(Template.getTopic(it.id_room.toString()))
                        startMatch(idLevel, idSoal, it.id_room.toString(), it.id_game.toString())
                    }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onStop() {
        super.onStop()
    }
}