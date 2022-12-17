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
import com.tugasakhir.welearn.core.utils.*
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
    private lateinit var sessionManager: SharedPreference
    private var answer: String ?= null

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
        main()

        refreshCanvasOnClick()
        back()
        initializeCanvas()
    }

    private fun initializeCanvas() {
        binding.cnvsLevelSatuAngkaOne.setStrokeWidth(30f)
        binding.cnvsLevelSatuAngkaTwo.setStrokeWidth(30f)
    }

    private fun main() {
        binding.btnUserParticipantA1.visibility = View.INVISIBLE
        val idSoal = AngkaLevelSatuFragmentArgs.fromBundle(arguments as Bundle).idSoal
        showScreen(idSoal)
        binding.submitSatuAngka.setOnClickListener{
            var score = 0
            val canvas1 = binding.cnvsLevelSatuAngkaOne.getBitmap().scale(224, 224)
            val canvas2 = binding.cnvsLevelSatuAngkaTwo.getBitmap().scale(224, 224)
            val result1 = Predict.PredictAngka(activity!!, canvas1, answer?.get(0)!!)
            val result2 = Predict.PredictAngka(activity!!, canvas2, answer?.get(1)!!)
            if (result1 + result2 == 20){
                score = 10
            }
            submitDrawing(idSoal, score)
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

    private fun showScreen(id: Int) {
        binding.progressBarA1.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id).collectLatest {
                    showData(it)
                    answer = it.jawaban
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

    private fun submitDrawing(id: Int, score: Int) {
        binding.progressBarA1.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id, score)
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

}