package com.tugasakhir.welearn.presentation.ui.huruf.singleplayer.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.core.utils.*
import com.tugasakhir.welearn.databinding.FragmentHurufLevelDuaBinding
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.soal.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HurufLevelDuaFragment : Fragment() {

    private var _binding: FragmentHurufLevelDuaBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
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
            val result1 = Predict.predictHuruf(activity!!, canvas1, answer?.get(0)!!)
            val result2 = Predict.predictHuruf(activity!!, canvas2, answer?.get(1)!!)
            val result3 = Predict.predictHuruf(activity!!, canvas3, answer?.get(2)!!)
            val result4 = Predict.predictHuruf(activity!!, canvas4, answer?.get(3)!!)
            val result5 = Predict.predictHuruf(activity!!, canvas5, answer?.get(4)!!)
            if ((result1 + result2 + result3 + result4 + result5) == 50) {
                score =  10
            }
            submitDrawing(idSoal, score!!)
            Template.saveMediaToStorage(canvas1, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
            Template.saveMediaToStorage(canvas2, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
            Template.saveMediaToStorage(canvas3, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
            Template.saveMediaToStorage(canvas4, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
            Template.saveMediaToStorage(canvas5, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
        }
    }

    private fun submitDrawing(id: Int, score: Int) {
        binding.progressBarH2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufPresenter.predictHuruf(id, score)
                    .collectLatest {
                        binding.progressBarH2.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredict(
                            context!!,
                            { view?.findNavController()?.navigate(HurufLevelDuaFragmentDirections.toScoreHurufDua()) },
                            score,
                        )
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarH2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id).collectLatest {
                    showData(it)
                    binding.progressBarH2.visibility = View.INVISIBLE
                    refreshCanvas()
                    answer = it.jawaban
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
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
}