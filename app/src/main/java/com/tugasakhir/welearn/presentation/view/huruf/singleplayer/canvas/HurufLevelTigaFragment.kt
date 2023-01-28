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
import com.tugasakhir.welearn.databinding.FragmentHurufLevelTigaBinding
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HurufLevelTigaFragment : Fragment() {

    private var _binding: FragmentHurufLevelTigaBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
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
            val result1 = Predict.predictHuruf(activity!!, canvas1, answer?.get(0)!!)
            val result2 = Predict.predictHuruf(activity!!, canvas2, answer?.get(1)!!)
            val result3 = Predict.predictHuruf(activity!!, canvas3, answer?.get(2)!!)
            val result4 = Predict.predictHuruf(activity!!, canvas4, answer?.get(3)!!)
            val result5 = Predict.predictHuruf(activity!!, canvas5, answer?.get(4)!!)
            val result6 = Predict.predictHuruf(activity!!, canvas6, answer?.get(5)!!)
            val result7 = Predict.predictHuruf(activity!!, canvas7, answer?.get(6)!!)
            val result8 = Predict.predictHuruf(activity!!, canvas8, answer?.get(7)!!)
            val result9 = Predict.predictHuruf(activity!!, canvas9, answer?.get(8)!!)
            if ((result1 + result2 + result3 + result4 + result5 + result6 +result7 + result8 +result9) == 90){
                score = 10
            }
            submitDrawing(idSoal, score)
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

    private fun submitDrawing(id: Int, score : Int) {
        binding.progressBarH3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufPresenter.predictHuruf(id, score, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        binding.progressBarH3.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredict(
                            context!!,
                            { view?.findNavController()?.navigate(HurufLevelTigaFragmentDirections.toScoreHurufTiga()) },
                            score,
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
                    show(it)
                    answer = it.jawaban
                    binding.progressBarH3.visibility = View.INVISIBLE
                    refreshCanvas()
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

}