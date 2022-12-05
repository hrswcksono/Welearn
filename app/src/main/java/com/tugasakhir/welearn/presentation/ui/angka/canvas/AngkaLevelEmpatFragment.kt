package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.Template
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.databinding.FragmentAngkaLevelEmpatBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.score.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreAngkaUserActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class AngkaLevelEmpatFragment : Fragment() {

    private var _binding: FragmentAngkaLevelEmpatBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictAngkaPresenter: PredictAngkaPresenter by viewModel()
    private val predictAngkaMultiPresenter: PredictAngkaMultiPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private val endGamePresenter: EndGamePresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val listUserParticipantPresenter: UserParticipantPresenter by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAngkaLevelEmpatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mode = AngkaLevelNolFragmentArgs.fromBundle(arguments as Bundle).gameMode
        if (mode == " "){
            arguments?.getString("mode")?.let { handlingMode(it) }
        } else{
            handlingMode(mode)
        }

        refreshCanvas()
        back()
    }

    private fun handlingMode(mode: String) {
        if (mode == "multi") {
            enableButton()
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
            binding.submitEmpatAngka.setOnClickListener {
                disableButton()
                val image = ArrayList<String>()
                image.add(Template.encodeImage(binding.cnvsLevelEmpatAngkaOne.getBitmap())!!)
                image.add(Template.encodeImage(binding.cnvsLevelEmpatAngkaTwo.getBitmap())!!)
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
            binding.btnUserParticipantA4.visibility = View.INVISIBLE
            val idSoal = AngkaLevelEmpatFragmentArgs.fromBundle(arguments as Bundle).idSoal
            showScreen(idSoal)
            binding.submitEmpatAngka.setOnClickListener{
                val image = ArrayList<String>()
                image.add(Template.encodeImage(binding.cnvsLevelEmpatAngkaOne.getBitmap())!!)
                image.add(Template.encodeImage(binding.cnvsLevelEmpatAngkaTwo.getBitmap())!!)
                disableButton()
                submitDrawing(idSoal, image)
            }
        }
    }

    private fun disableButton(){
        binding.submitEmpatAngka.isEnabled = false
        binding.submitEmpatAngka.isClickable = false
    }

    private fun enableButton(){
        binding.submitEmpatAngka.isEnabled = true
        binding.submitEmpatAngka.isClickable = true
    }

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

    private fun submitDrawing(id: Int, image: ArrayList<String>) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id.toInt(), image)
                    .collectLatest {
                        binding.progressBarA4.visibility = View.INVISIBLE
                        activity?.let { it1 ->
                            CustomDialogBox.withConfirm(
                                it1,
                                SweetAlertDialog.SUCCESS_TYPE,
                                "Berhasil Menjawab",
                                it.message
                            ) {
                                startActivity(
                                    Intent(
                                        activity,
                                        ScoreAngkaUserActivity::class.java
                                    )
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id.toInt()).collectLatest {
                    showData(it)
                    binding.progressBarA4.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
        Template.speak(data.keterangan + " " + data.soal)
        binding.spkEmpatAngka.setOnClickListener {
            Template.speak(data.keterangan + " " + data.soal)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoalAngkaEmpat.text = data.soal
    }

    private fun refreshCanvasOnClick(){
        binding.refreshEmpatAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelEmpatAngkaOne.clearCanvas()
        binding.cnvsLevelEmpatAngkaTwo.clearCanvas()
    }

    private fun back(){
        binding.levelEmpatAngkaBack.setOnClickListener {
            view?.findNavController()?.navigate(AngkaLevelEmpatFragmentDirections.backFromAngkaEmpat())
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
        binding.btnUserParticipantA4.setOnClickListener {
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