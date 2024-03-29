package com.tugasakhir.welearn.presentation.view.angka.singleplayer.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.utils.*
import com.tugasakhir.welearn.databinding.FragmentAngkaLevelSatuBinding
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.InGamePresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AngkaLevelSatuFragment : Fragment() {

    private var _binding: FragmentAngkaLevelSatuBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: InGamePresenter by viewModel()
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
            val (result1, accuracy1) = Predict.PredictAngkaCoba(activity!!, canvas1)
            val (result2, accuracy2) = Predict.PredictAngkaCoba(activity!!, canvas2)
            if (result1 == answer?.get(0) && result2 == answer?.get(1)){
                score = 10
            }
            submitDrawing(idSoal, score, dialogText(result1, accuracy1, result2, accuracy2))
        }
    }

    private fun dialogText(answer1: Char, accuracy1: Float, answer2: Char, accuracy2: Float) : String {
        return "Jawaban kamu $answer1$answer2\n  Ketelitian pada kanvas 1 : ${(accuracy1 * 100).toInt()}%\n" +
                "Ketelitian pada kanvas 2 : ${(accuracy2 * 100).toInt()}%"
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
                soalViewModel.getSoalByID(id, sessionManager.fetchAuthToken()!!).collectLatest {
                    when(it) {
                        is Resource.Success ->{
                            showData(it.data!!)
                            answer = it.data.jawaban
                            binding.progressBarA1.visibility = View.INVISIBLE
                            refreshCanvas()
                        }
                        is Resource.Loading ->{}
                        is Resource.Error ->{
//                            binding.progressBar4.visibility = View.GONE
                            CustomDialogBox.flatDialog(context!!, "Kesalahan Server", it.message.toString())
                        }
                    }
                }
            }
        }
    }

    private fun showData(data: Soal){
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

    private fun submitDrawing(id: Int, score: Int, message: String) {
        binding.progressBarA1.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id, score, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        binding.progressBarA1.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredictCoba(
                            requireContext(),
                            { view?.findNavController()?.navigate(AngkaLevelSatuFragmentDirections.toScoreAngkaSatu()) },
                            score,
                            message
                        )
                    }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}