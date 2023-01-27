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
import com.tugasakhir.welearn.core.utils.ExitApp
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
            inputLevel = 0
            randomSoal(inputLevel)
        }
        binding.bhLevel1.setOnClickListener {
            inputLevel = 1
            randomSoal(inputLevel)
        }
        binding.bhLevel2.setOnClickListener {
            inputLevel = 2
            randomSoal(inputLevel)
        }
        binding.bhLevel3.setOnClickListener {
            inputLevel = 3
            randomSoal(inputLevel)
        }
        binding.bhLevel4.setOnClickListener {
//            eraseCheckLevel()
//            binding.alevel4.visibility = View.VISIBLE
            inputLevel = 4
            randomSoal(inputLevel)
        }
    }

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
                            startMatch(inputLevel)
                        }
                    }
                }
            }
        }
    }

    private fun startMatch(idLevel: Int) {
        binding.btnStartAngka.setOnClickListener{
            makeRoom(idLevel)
        }
    }

    private fun makeRoom(idLevel: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                makeRoomPresenter.makeRoom(2, idLevel, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        sessionManager.saveIDRoom(it.id_room)
                        ExitApp.topic = it.id_room.toString()
                        binding.tvIdRoomAngka.text = it.id_room.toString()
                        FirebaseMessaging.getInstance().subscribeToTopic(it.id_room.toString())
                    }
            }
        }
    }
}