package com.tugasakhir.welearn.presentation.view.huruf.singleplayer.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.utils.*
import com.tugasakhir.welearn.databinding.FragmentHurufLevelDuaBinding
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.InGamePresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HurufLevelDuaFragment : Fragment() {

    private var _binding: FragmentHurufLevelDuaBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: InGamePresenter by viewModel()
    private val predictHurufPresenter: PredictHurufPresenter by viewModel()
    private var answer: String ?= null
    private lateinit var sessionManager: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHurufLevelDuaBinding.inflate(inflater, container, false)
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
        binding.cnvsLevelDuaHurufone.setStrokeWidth(30f)
        binding.cnvsLevelDuaHuruftwo.setStrokeWidth(30f)
        binding.cnvsLevelDuaHurufthree.setStrokeWidth(30f)
        binding.cnvsLevelDuaHuruffour.setStrokeWidth(30f)
        binding.cnvsLevelDuaHuruffive.setStrokeWidth(30f)
    }

    private fun main() {
        binding.btnUserParticipantH2.visibility = View.INVISIBLE
        val idSoal = HurufLevelDuaFragmentArgs.fromBundle(arguments as Bundle).idSoal
        showScreen(idSoal)
        binding.submitDuaHuruf.setOnClickListener{
            var score = 0
            val canvas1 = binding.cnvsLevelDuaHurufone.getBitmap().scale(224, 224)
            val canvas2 = binding.cnvsLevelDuaHuruftwo.getBitmap().scale(224, 224)
            val canvas3 = binding.cnvsLevelDuaHurufthree.getBitmap().scale(224, 224)
            val canvas4 = binding.cnvsLevelDuaHuruffour.getBitmap().scale(224, 224)
            val canvas5 = binding.cnvsLevelDuaHuruffive.getBitmap().scale(224, 224)
            val (result1, accuracy1) = Predict.predictHurufCoba(activity!!, canvas1)
            val (result2, accuracy2) = Predict.predictHurufCoba(activity!!, canvas2)
            val (result3, accuracy3) = Predict.predictHurufCoba(activity!!, canvas3)
            val (result4, accuracy4) = Predict.predictHurufCoba(activity!!, canvas4)
            val (result5, accuracy5) = Predict.predictHurufCoba(activity!!, canvas5)
            if (result1 == answer?.get(0) && result2 == answer?.get(1) && result3 == answer?.get(2) && result4 == answer?.get(3)&& result5 == answer?.get(4)){
                score =  10
            }
            submitDrawing(idSoal, score, dialogText(result1, accuracy1, result2, accuracy2, result3, accuracy3, result4, accuracy4, result5, accuracy5))
        }
    }
    private fun dialogText(answer1: Char, accuracy1: Float, answer2: Char, accuracy2: Float, answer3: Char, accuracy3: Float, answer4: Char, accuracy4: Float, answer5: Char, accuracy5: Float) : String {
        return "Jawaban kamu $answer1, $answer2, $answer3, $answer4, $answer5  dengan Ketelitian ${(accuracy1*100).toInt()}%, ${(accuracy2*100).toInt()}%, ${(accuracy3*100).toInt()}%, ${(accuracy4*100).toInt()}%, ${(accuracy5*100).toInt()}%\n"
    }

    private fun submitDrawing(id: Int, score: Int, message: String) {
        binding.progressBarH2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufPresenter.predictHuruf(id, score, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        binding.progressBarH2.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredictCoba(
                            context!!,
                            { view?.findNavController()?.navigate(HurufLevelDuaFragmentDirections.toScoreHurufDua()) },
                            score,
                            message
                        )
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarH2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id, sessionManager.fetchAuthToken()!!).collectLatest {
                    showData(it)
                    binding.progressBarH2.visibility = View.INVISIBLE
                    refreshCanvas()
                    answer = it.jawaban
                }
            }
        }
    }

    private fun showData(data: Soal){
        Template.speak(data.keterangan)
        binding.spkDuaHuruf.setOnClickListener {
            Template.speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = data.keterangan
        binding.levelHurufKe.text = "Level ke ${data.idLevel}"
    }

    private fun refreshCanvasOnClick(){
        binding.refreshDuaHuruf.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelDuaHurufone.clearCanvas()
        binding.cnvsLevelDuaHuruftwo.clearCanvas()
        binding.cnvsLevelDuaHurufthree.clearCanvas()
        binding.cnvsLevelDuaHuruffour.clearCanvas()
        binding.cnvsLevelDuaHuruffive.clearCanvas()
    }

    private fun back(){
        binding.levelDuaHurufBack.setOnClickListener {
            val backSoal = HurufLevelDuaFragmentDirections.actionHurufLevelDuaNavToListSoalHurufNav()
            backSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backSoal)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}