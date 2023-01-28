package com.tugasakhir.welearn.presentation.view.huruf.multiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.utils.CustomDialogBox
import com.tugasakhir.welearn.utils.ExitApp
import com.tugasakhir.welearn.utils.SharedPreference
import com.tugasakhir.welearn.databinding.FragmentMatchHurufBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.presentation.presenter.multiplayer.MakeRoomPresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationPresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.RandomIDSoalMultiPresenter
import com.tugasakhir.welearn.utils.Template
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchHurufFragment : Fragment() {

    private var _binding: FragmentMatchHurufBinding ?= null
    private val binding get() = _binding!!
    private val pushNotif: PushNotificationPresenter by viewModel()
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

        choseeLevel()
    }

    private fun choseeLevel(){
        var inputLevel: Int
        binding.baLevel0.setOnClickListener {
            refreshButton()
            binding.baLevel0.setBackgroundResource(R.drawable.yellow_j)
            inputLevel = 0
            randomSoal(inputLevel)
        }
        binding.baLevel1.setOnClickListener {
            refreshButton()
            binding.baLevel1.setBackgroundResource(R.drawable.yellow_j)
            inputLevel = 1
            randomSoal(inputLevel)
        }
        binding.baLevel2.setOnClickListener {
            refreshButton()
            binding.baLevel2.setBackgroundResource(R.drawable.yellow_j)
            inputLevel = 2
            randomSoal(inputLevel)
        }
        binding.baLevel3.setOnClickListener {
            refreshButton()
            binding.baLevel3.setBackgroundResource(R.drawable.yellow_j)
            inputLevel = 3
            randomSoal(inputLevel)
        }
    }

    private fun refreshButton(){
        binding.baLevel0.setBackgroundResource(R.drawable.bg_grid)
        binding.baLevel1.setBackgroundResource(R.drawable.bg_grid)
        binding.baLevel2.setBackgroundResource(R.drawable.bg_grid)
        binding.baLevel3.setBackgroundResource(R.drawable.bg_grid)
    }

    private fun randomSoal(inputLevel: Int) {
        binding.btnHurufAcak.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelRandom.randomIDSoalMultiByLevel(1,
                        inputLevel, sessionManager.fetchAuthToken()!!
                    ).collectLatest {
                        if (it.id_soal.isNotEmpty()){
                            CustomDialogBox.dialogSoalMulti(context!!)
                            makeRoom(inputLevel, it.id_soal)
                            binding.btnHurufAcak.setBackgroundResource(R.drawable.yellow)
                        }
                    }
                }
            }
        }
    }

    private fun startMatch(idLevel: Int, idSoal: String, topic: String, id_game: String) {
        binding.btnStartHuruf.setOnClickListener{
            binding.btnStartHuruf.setBackgroundResource(R.drawable.yellow)
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    pushNotif.pushNotification(
                        PushNotification(
                            NotificationData(
                                "Pertandingan telah dimulai",
                                "Selamat mengerjakan !",
                                "starthuruf",
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

    private fun makeRoom(idLevel: Int, idSoal: String){
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Default) {
                makeRoomPresenter.makeRoom(1, idLevel, sessionManager.fetchAuthToken()!!).collectLatest {
                    sessionManager.saveIDRoom(it.id_room)
                    ExitApp.topic = it.id_room.toString()
                    binding.tvIdRoom.text = it.id_room.toString()
                    FirebaseMessaging.getInstance().subscribeToTopic(Template.getTopic(it.id_room.toString()))
                    startMatch(idLevel, idSoal, it.id_room.toString(), it.id_game.toString())
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}