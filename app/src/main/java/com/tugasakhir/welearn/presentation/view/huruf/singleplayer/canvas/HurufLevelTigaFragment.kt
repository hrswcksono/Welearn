package com.tugasakhir.welearn.presentation.view.huruf.singleplayer.canvas

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
import com.tugasakhir.welearn.databinding.FragmentHurufLevelTigaBinding
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.InGamePresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HurufLevelTigaFragment : Fragment() {

    private var _binding: FragmentHurufLevelTigaBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: InGamePresenter by viewModel()
    private val predictHurufPresenter: PredictHurufPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: String ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHurufLevelTigaBinding.inflate(inflater, container, false)
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
        binding.cnvsLevelTigaHurufone.setStrokeWidth(30f)
        binding.cnvsLevelTigaHuruftwo.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufthree.setStrokeWidth(30f)
        binding.cnvsLevelTigaHuruffour.setStrokeWidth(30f)
        binding.cnvsLevelTigaHuruffive.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufsix.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufseven.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufeight.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufnine.setStrokeWidth(30f)
    }

    private fun main() {
        binding.btnUserParticipantH3.visibility = View.INVISIBLE
        val idSoal = HurufLevelTigaFragmentArgs.fromBundle(arguments as Bundle).idSoal
        showScreen(idSoal)
        binding.submitTigaHuruf.setOnClickListener{
            var score = 0
            val canvas1 = binding.cnvsLevelTigaHurufone.getBitmap().scale(224, 224)
            val canvas2 = binding.cnvsLevelTigaHuruftwo.getBitmap().scale(224, 224)
            val canvas3 = binding.cnvsLevelTigaHurufthree.getBitmap().scale(224, 224)
            val canvas4 = binding.cnvsLevelTigaHuruffour.getBitmap().scale(224, 224)
            val canvas5 = binding.cnvsLevelTigaHuruffive.getBitmap().scale(224, 224)
            val canvas6 = binding.cnvsLevelTigaHurufsix.getBitmap().scale(224, 224)
            val canvas7 = binding.cnvsLevelTigaHurufseven.getBitmap().scale(224, 224)
            val canvas8 = binding. cnvsLevelTigaHurufeight.getBitmap().scale(224, 224)
            val canvas9 = binding.cnvsLevelTigaHurufnine.getBitmap().scale(224, 224)
            val (result1, accuracy1) = Predict.predictHurufCoba(activity!!, canvas1)
            val (result2, accuracy2) = Predict.predictHurufCoba(activity!!, canvas2)
            val (result3, accuracy3) = Predict.predictHurufCoba(activity!!, canvas3)
            val (result4, accuracy4) = Predict.predictHurufCoba(activity!!, canvas4)
            val (result5, accuracy5) = Predict.predictHurufCoba(activity!!, canvas5)
            val (result6, accuracy6) = Predict.predictHurufCoba(activity!!, canvas6)
            val (result7, accuracy7) = Predict.predictHurufCoba(activity!!, canvas7)
            val (result8, accuracy8) = Predict.predictHurufCoba(activity!!, canvas8)
            val (result9, accuracy9) = Predict.predictHurufCoba(activity!!, canvas9)
            if (result1 == answer?.get(0) && result2 == answer?.get(1) && result3 == answer?.get(2) && result4 == answer?.get(3) && result5 == answer?.get(4)&& result6 == answer?.get(5)&& result7 == answer?.get(6)&& result8 == answer?.get(7)&& result9 == answer?.get(8)){
                score =  10
            }
            submitDrawing(idSoal, score, dialogText(result1, accuracy1, result2, accuracy2, result3, accuracy3, result4, accuracy4, result5, accuracy5, result6, accuracy6, result7, accuracy7, result8, accuracy8, result9, accuracy9))
        }
    }
    private fun dialogText(answer1: Char, accuracy1: Float, answer2: Char, accuracy2: Float, answer3: Char, accuracy3: Float, answer4: Char, accuracy4: Float, answer5: Char, accuracy5: Float, answer6: Char, accuracy6: Float, answer7: Char, accuracy7: Float, answer8: Char, accuracy8: Float, answer9: Char, accuracy9: Float) : String {
        return "Jawaban kamu $answer1, $answer2, $answer3, $answer4, $answer5, $answer6, $answer7, $answer8, $answer9  dengan Ketelitian ${(accuracy1*100).toInt()}%, ${(accuracy2*100).toInt()}%, ${(accuracy3*100).toInt()}%, ${(accuracy4*100).toInt()}%, ${(accuracy5*100).toInt()}%, ${(accuracy6*100).toInt()}%, ${(accuracy7*100).toInt()}%, ${(accuracy8*100).toInt()}%, ${(accuracy9*100).toInt()}%\n"
    }

    private fun disableButton(){
        binding.submitTigaHuruf.isEnabled = false
        binding.submitTigaHuruf.isClickable = false
    }

    private fun enableButton(){
        binding.submitTigaHuruf.isEnabled = true
        binding.submitTigaHuruf.isClickable = true
    }

    private fun submitDrawing(id: Int, score : Int, message: String) {
        binding.progressBarH3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufPresenter.predictHuruf(id, score, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        binding.progressBarH3.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredictCoba(
                            context!!,
                            { view?.findNavController()?.navigate(HurufLevelTigaFragmentDirections.toScoreHurufTiga()) },
                            score,
                            message
                        )
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarH3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id, sessionManager.fetchAuthToken()!!).collectLatest {
                    when(it) {
                        is Resource.Success ->{
                            show(it.data!!)
                            answer = it.data.jawaban
                            binding.progressBarH3.visibility = View.INVISIBLE
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

    private fun show(data: Soal){
        Template.speak(data.keterangan)
        binding.spkTigaHuruf.setOnClickListener {
            Template.speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = data.keterangan
        binding.levelHurufKe.text = "Level ke ${data.idLevel}"
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
            val backSoal = HurufLevelTigaFragmentDirections.actionHurufLevelTigaNavToListSoalHurufNav()
            backSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backSoal)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}