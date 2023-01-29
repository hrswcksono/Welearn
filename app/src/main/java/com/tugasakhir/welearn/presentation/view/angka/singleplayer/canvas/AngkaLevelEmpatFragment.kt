package com.tugasakhir.welearn.presentation.view.angka.singleplayer.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.utils.*
import com.tugasakhir.welearn.databinding.FragmentAngkaLevelEmpatBinding
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.InGamePresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AngkaLevelEmpatFragment : Fragment() {

    private var _binding: FragmentAngkaLevelEmpatBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: InGamePresenter by viewModel()
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
            val (result1, accuracy1) = Predict.PredictAngkaCoba(activity!!, canvas1)
            val (result2, accuracy2) = Predict.PredictAngkaCoba(activity!!, canvas2)
            if (result1 == answer?.get(0) && result2 == answer?.get(1)){
                score = 10
            }
            submitDrawing(idSoal, score, dialogText(result1, accuracy1, result2, accuracy2))
        }
    }

    private fun dialogText(answer1: Char, accuracy1: Float, answer2: Char, accuracy2: Float) : String {
        return "Jawaban kamu $answer1  Ketelitian ${(accuracy1*100).toInt()}%\n" +
                "Jawaban kamu $answer2  Ketelitian ${(accuracy2*100).toInt()}%"
    }

    private fun disableButton(){
        binding.submitEmpatAngka.isEnabled = false
        binding.submitEmpatAngka.isClickable = false
    }

    private fun enableButton(){
        binding.submitEmpatAngka.isEnabled = true
        binding.submitEmpatAngka.isClickable = true
    }

    private fun submitDrawing(id: Int, score: Int, message: String) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id, score, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        binding.progressBarA4.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredictCoba(
                            requireContext(),
                            { view?.findNavController()?.navigate(AngkaLevelEmpatFragmentDirections.toScoreAngkaEmpat()) },
                            score,
                            message
                        )
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id, sessionManager.fetchAuthToken()!!).collectLatest {
                    showData(it)
                    answer = it.jawaban
                    binding.progressBarA4.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: Soal){
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}