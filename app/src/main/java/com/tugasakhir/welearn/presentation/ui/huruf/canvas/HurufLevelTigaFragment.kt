package com.tugasakhir.welearn.presentation.ui.huruf.canvas

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
import com.tugasakhir.welearn.databinding.FragmentHurufLevelTigaBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.score.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class HurufLevelTigaFragment : Fragment() {

    private var _binding: FragmentHurufLevelTigaBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictHurufMultiPresenter: PredictHurufMultiPresenter by viewModel()
    private val predictHurufPresenter: PredictHurufPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private val endGamePresenter: EndGamePresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val listUserParticipantPresenter: UserParticipantPresenter by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHurufLevelTigaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mode = HurufLevelTigaFragmentArgs.fromBundle(arguments as Bundle).gameMode
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
            binding.submitTigaHuruf.setOnClickListener {
                disableButton()
                val image = ArrayList<String>()
                image.apply {
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufone.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHuruftwo.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufthree.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHuruffour.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHuruffive.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufsix.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufseven.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufeight.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufnine.getBitmap()))
                }
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
            binding.btnUserParticipantH3.visibility = View.INVISIBLE
            val idSoal = HurufLevelTigaFragmentArgs.fromBundle(arguments as Bundle).idSoal
            showScreen(idSoal)
            binding.submitTigaHuruf.setOnClickListener{
                val image = ArrayList<String>()
                image.apply {
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufone.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHuruftwo.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufthree.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHuruffour.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHuruffive.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufsix.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufseven.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufeight.getBitmap()))
                    add(Template.encodeImage(binding.cnvsLevelTigaHurufnine.getBitmap()))
                }
                disableButton()
                submitDrawing(idSoal, image)
            }
        }
    }

    private fun disableButton(){
        binding.submitTigaHuruf.isEnabled = false
        binding.submitTigaHuruf.isClickable = false
    }

    private fun enableButton(){
        binding.submitTigaHuruf.isEnabled = true
        binding.submitTigaHuruf.isClickable = true
    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, image: ArrayList<String>){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufMultiPresenter.predictHurufMulti(idGame, idSoal, image,  duration)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun submitDrawing(id: Int, image: ArrayList<String>) {
        binding.progressBarH3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufPresenter.predictHuruf(id.toInt(), image)
                    .collectLatest {
                        binding.progressBarH3.visibility = View.INVISIBLE
                        activity?.let { it1 ->
                            CustomDialogBox.withConfirm(
                                it1,
                                SweetAlertDialog.SUCCESS_TYPE,
                                "Berhasil Menjawab",
                                it.message
                            ) {
                                view?.findNavController()?.navigate(HurufLevelTigaFragmentDirections.toScoreHurufTiga())
                            }
                        }
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarH3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id.toInt()).collectLatest {
                    show(it)
                    binding.progressBarH3.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun show(data: SoalEntity){
        Template.speak(data.keterangan)
        binding.spkTigaHuruf.setOnClickListener {
            Template.speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = data.keterangan
        binding.levelHurufKe.text = "Level ke ${data.idLevel}"
    }

    private fun draw() {
    }

    private fun refreshCanvasOnClick(){
        binding.refreshTigaHuruf.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelTigaHurufone.clearCanvas()
        binding.cnvsLevelTigaHuruftwo.clearCanvas()
        binding.cnvsLevelTigaHurufthree.clearCanvas()
        binding.cnvsLevelTigaHuruffour.clearCanvas()
        binding.cnvsLevelTigaHuruffive.clearCanvas()
        binding.cnvsLevelTigaHurufsix.clearCanvas()
        binding.cnvsLevelTigaHurufseven.clearCanvas()
        binding.cnvsLevelTigaHurufeight.clearCanvas()
        binding.cnvsLevelTigaHurufnine.clearCanvas()
    }

    private fun back(){
        binding.levelTigaHurufBack.setOnClickListener {
            view?.findNavController()?.navigate(HurufLevelTigaFragmentDirections.backFromHurufTiga())
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
                        Constants.TOPIC_JOIN_HURUF,
                        "high"
                    )
                ).collectLatest {  }
            }
        }
    }

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantH3.setOnClickListener {
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