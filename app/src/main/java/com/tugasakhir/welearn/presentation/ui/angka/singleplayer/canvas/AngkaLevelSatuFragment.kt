package com.tugasakhir.welearn.presentation.ui.angka.singleplayer.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.core.utils.Template
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.databinding.FragmentAngkaLevelSatuBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.score.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class AngkaLevelSatuFragment : Fragment() {

    private var _binding: FragmentAngkaLevelSatuBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictAngkaPresenter: PredictAngkaPresenter by viewModel()
    private val predictAngkaMultiPresenter: PredictAngkaMultiPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private val endGamePresenter: EndGamePresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val listUserParticipantPresenter: UserParticipantPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAngkaLevelSatuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = activity?.let { SharedPreference(it) }!!
        val mode = AngkaLevelSatuFragmentArgs.fromBundle(arguments as Bundle).gameMode
        if (mode == " "){
            arguments?.getString("mode")?.let { handlingMode(it) }
        } else{
            handlingMode(mode)
        }

        refreshCanvasOnClick()
        back()
    }

    private fun handlingMode(mode: String) {
        if (mode == "multi") {
            val soalID = arguments?.getString("idSoal")
            val arrayID = soalID.toString().split("|")
            val idGame = arguments?.getString("idGame")
            listDialog(idGame!!.toInt())
            joinGame(idGame!!.toInt())
            var index = 0
            var total = 0L
            val begin = Date().time
//            Toast.makeText(this, idGame.toString(), Toast.LENGTH_SHORT).show()
            var idSoal = arrayID[index]
//            Toast.makeText(this, idSoal, Toast.LENGTH_SHORT).show()
            showScreen(idSoal.toInt())
            binding.submitSatuAngka.setOnClickListener {

                val image = ArrayList<String>()
                image.add(Template.encodeImage(binding.cnvsLevelSatuAngkaOne.getBitmap().scale(100,100))!!)
                image.add(Template.encodeImage(binding.cnvsLevelSatuAngkaTwo.getBitmap().scale(100,100))!!)
//                Toast.makeText(this, idSoal, Toast.LENGTH_SHORT).show()
                val end = Date().time
                total = (end - begin)/1000
//                Toast.makeText(this, total.toString(), Toast.LENGTH_SHORT).show()
                submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), image)
                index++
                if (index < 3) {
                    idSoal = arrayID[index]
                    showScreen(idSoal.toInt())
//                    submitDrawing(idSoal)
                }

            }
        }else if (mode == "single") {
            binding.btnUserParticipantA1.visibility = View.INVISIBLE
            val idSoal = AngkaLevelSatuFragmentArgs.fromBundle(arguments as Bundle).idSoal
            showScreen(idSoal)
            binding.submitSatuAngka.setOnClickListener{
                val image = ArrayList<String>()
                image.add(Template.encodeImage(binding.cnvsLevelSatuAngkaOne.getBitmap().scale(100,100))!!)
                image.add(Template.encodeImage(binding.cnvsLevelSatuAngkaTwo.getBitmap().scale(100,100))!!)
                submitDrawing(idSoal, image)
            }
        }
    }

//    private fun disableButton(){
//        binding.submitSatuAngka.isEnabled = false
//        binding.submitSatuAngka.isClickable = false
//    }
//
//    private fun enableButton(){
//        binding.submitSatuAngka.isEnabled = true
//        binding.submitSatuAngka.isClickable = true
//    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, image: ArrayList<String>){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaMultiPresenter.predictAngkaMulti(idGame, idSoal, image,  duration)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarA1.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id).collectLatest {
                    showData(it)
                    binding.progressBarA1.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
        Template.speak(data.keterangan + " " + data.soal)
        binding.spkSatuAngka.setOnClickListener {
            Template.speak(data.keterangan + " " + data.soal)
        }

        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoalAngkaSatu.text = data.soal
    }

    private fun refreshCanvasOnClick(){
        binding.refreshSatuAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelSatuAngkaOne.clearCanvas()
        binding.cnvsLevelSatuAngkaTwo.clearCanvas()
    }

    private fun back(){
        binding.levelSatuAngkaBack.setOnClickListener {
            val backSoal = AngkaLevelSatuFragmentDirections.actionAngkaLevelSatuNavToListSoalAngkaNav()
            backSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backSoal)
        }
    }

    private fun submitDrawing(id: Int, image: ArrayList<String>) {
        binding.progressBarA1.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id, image)
                    .collectLatest {
                        binding.progressBarA1.visibility = View.INVISIBLE
                        activity?.let { it1 ->
                            CustomDialogBox.withConfirm(
                                it1,
                                SweetAlertDialog.SUCCESS_TYPE,
                                "Berhasil Menjawab",
                                it.message
                            ) {
                                view?.findNavController()?.navigate(AngkaLevelSatuFragmentDirections.toScoreAngkaSatu())
                            }
                        }
                    }
            }
        }
    }

    private fun joinGame(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Default) {
                joinGamePresenter.joinGame(idGame.toString())
                    .collectLatest {  }
            }
        }
    }

    private fun endGame(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                endGamePresenter.endGame(idGame.toString())
                    .collectLatest {
                        if (it == "Berhasil End Game"){
//                            Toast.makeText(this@HurufLevelNolActivity, "Pindah", Toast.LENGTH_SHORT).show()
                            showScoreMulti(idGame.toString())
                        }
                    }
            }
        }
    }

    private fun showScoreMulti(idGame: String) {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                pushNotification.pushNotification(
                    PushNotification(
                        NotificationData(
                            "Selesai"
                            ,"Pertandingan telah selesai"
                            ,"score",
                            "",
                            0,
                            idGame
                        ),
                        Constants.TOPIC_JOIN_ANGKA,
                        "high"
                    )
                ).collectLatest {  }
            }
        }
    }

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantA1.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    listUserParticipantPresenter.getListUserParticipant(idGame).collectLatest {
                        when(it) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                activity?.let { it1 -> Template.listUser(it.data!!, it1) }
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
            }
        }
    }

}