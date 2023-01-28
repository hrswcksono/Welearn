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
import com.tugasakhir.welearn.databinding.FragmentHurufLevelSatuBinding
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

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
                predictHurufPresenter.predictHuruf(id, score, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        binding.progressBarH1.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredict(
                            context!!,
                            { view?.findNavController()?.navigate(HurufLevelSatuFragmentDirections.toScoreHurufSatu()) },
                            score,
                        )
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarH1.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id, sessionManager.fetchAuthToken()!!).collectLatest {
                    showData(it)
                    answer = it.jawaban
                    binding.progressBarH1.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: Soal){
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}