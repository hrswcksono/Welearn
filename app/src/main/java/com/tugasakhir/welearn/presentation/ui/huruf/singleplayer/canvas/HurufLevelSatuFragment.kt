package com.tugasakhir.welearn.presentation.ui.huruf.singleplayer.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.core.utils.*
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.databinding.FragmentHurufLevelSatuBinding
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

class HurufLevelSatuFragment : Fragment() {

    private var _binding: FragmentHurufLevelSatuBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictHurufPresenter: PredictHurufPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: String ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHurufLevelSatuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = activity?.let { SharedPreference(it) }!!
        main()

        initializeCanvas()
        refreshCanvasOnClick()
        back()
    }

    private fun initializeCanvas(){
        binding.cnvsLevelSatuHurufone.setStrokeWidth(30f)
        binding.cnvsLevelSatuHuruftwo.setStrokeWidth(30f)
        binding.cnvsLevelSatuHurufthree.setStrokeWidth(30f)
    }

    private fun main() {
        binding.btnUserParticipantH1.visibility = View.INVISIBLE
        val idSoal = HurufLevelSatuFragmentArgs.fromBundle(arguments as Bundle).idSoal
        showScreen(idSoal)
        binding.submitSatuHuruf.setOnClickListener{
            var score = 0
            val canvas1 = binding.cnvsLevelSatuHurufone.getBitmap().scale(224, 224)
            val canvas2 = binding.cnvsLevelSatuHuruftwo.getBitmap().scale(224, 224)
            val canvas3 = binding.cnvsLevelSatuHurufthree.getBitmap().scale(224, 224)
            val result1 = Predict.predictHuruf(activity!!, canvas1, answer?.get(0)!!)
            val result2 = Predict.predictHuruf(activity!!, canvas2, answer?.get(1)!!)
            val result3 = Predict.predictHuruf(activity!!, canvas3, answer?.get(2)!!)
            if ((result1 + result2 + result3) == 30) {
                score = 10
            }
            submitDrawing(idSoal, score)
            Template.saveMediaToStorage(canvas1, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
            Template.saveMediaToStorage(canvas2, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
            Template.saveMediaToStorage(canvas3, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
        }
    }

    private fun disableButton(){
        binding.submitSatuHuruf.isEnabled = false
        binding.submitSatuHuruf.isClickable = false
    }

    private fun enableButton(){
        binding.submitSatuHuruf.isEnabled = true
        binding.submitSatuHuruf.isClickable = true
    }

    private fun submitDrawing(id: Int, score : Int) {
        binding.progressBarH1.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufPresenter.predictHuruf(id, score)
                    .collectLatest {
                        binding.progressBarH1.visibility = View.INVISIBLE
                        activity?.let { it1 ->
                            CustomDialogBox.withConfirm(
                                it1,
                                SweetAlertDialog.SUCCESS_TYPE,
                                "Berhasil Menjawab",
                                it.message
                            ) {
                                view?.findNavController()?.navigate(HurufLevelSatuFragmentDirections.toScoreHurufSatu())
                            }
                        }
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarH1.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id).collectLatest {
                    showData(it)
                    answer = it.jawaban
                    binding.progressBarH1.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
        Template.speak(data.keterangan)
        binding.spkSatuHuruf.setOnClickListener {
            Template.speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = data.keterangan
        binding.levelHurufKe.text = "Level ke ${data.idLevel}"
    }

    private fun refreshCanvasOnClick(){
        binding.refreshSatuHuruf.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelSatuHurufone.clearCanvas()
        binding.cnvsLevelSatuHuruftwo.clearCanvas()
        binding.cnvsLevelSatuHurufthree.clearCanvas()
    }

    private fun back(){
        binding.levelSatuHurufBack.setOnClickListener {
            val backSoal = HurufLevelSatuFragmentDirections.actionHurufLevelSatuNavToListSoalHurufNav()
            backSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backSoal)
        }
    }
}