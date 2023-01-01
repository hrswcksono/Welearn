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
import com.tugasakhir.welearn.databinding.FragmentAngkaLevelEmpatBinding
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

class AngkaLevelEmpatFragment : Fragment() {

    private var _binding: FragmentAngkaLevelEmpatBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictAngkaPresenter: PredictAngkaPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: String ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAngkaLevelEmpatBinding.inflate(inflater, container, false)
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
        binding.cnvsLevelEmpatAngkaOne.setStrokeWidth(30f)
        binding.cnvsLevelEmpatAngkaTwo.setStrokeWidth(30f)
    }

    private fun main() {
        binding.btnUserParticipantA4.visibility = View.INVISIBLE
        val idSoal = AngkaLevelEmpatFragmentArgs.fromBundle(arguments as Bundle).idSoal
        showScreen(idSoal)
        binding.submitEmpatAngka.setOnClickListener{
            var score = 0
            val canvas1 = binding.cnvsLevelEmpatAngkaOne.getBitmap().scale(224, 224)
            val canvas2 = binding.cnvsLevelEmpatAngkaTwo.getBitmap().scale(224, 224)
            val result1 = Predict.PredictAngka(activity!!, canvas1, answer?.get(0)!!)
            val result2 = Predict.PredictAngka(activity!!, canvas2, answer?.get(1)!!)
            if (result1 + result2 == 20){
                score = 10
            }
            submitDrawing(idSoal, score)
            Template.saveMediaToStorage(canvas1, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
            Template.saveMediaToStorage(canvas2, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
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

    private fun submitDrawing(id: Int, score: Int) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id, score)
                    .collectLatest {
                        binding.progressBarA4.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredict(
                            context!!,
                            { view?.findNavController()?.navigate(AngkaLevelEmpatFragmentDirections.toScoreAngkaAmpat()) },
                            score,
                            0
                        )
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id).collectLatest {
                    showData(it)
                    answer = it.jawaban
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
            val backSoal = AngkaLevelEmpatFragmentDirections.actionAngkaLevelEmpatNavToListSoalAngkaNav()
            backSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backSoal)
        }
    }
}