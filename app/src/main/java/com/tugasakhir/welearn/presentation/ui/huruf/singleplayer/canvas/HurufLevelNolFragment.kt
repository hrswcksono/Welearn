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
import com.tugasakhir.welearn.databinding.FragmentHurufLevelNolBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.score.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import com.tugasakhir.welearn.presentation.ui.angka.singleplayer.canvas.AngkaLevelTigaFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList

class HurufLevelNolFragment : Fragment() {

    private var _binding: FragmentHurufLevelNolBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictHurufPresenter: PredictHurufPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: Char ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHurufLevelNolBinding.inflate(inflater, container, false)
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
        binding.cnvsLevelNolHuruf.setStrokeWidth(30f)
    }

    private fun main() {
        binding.btnUserParticipantH0.visibility = View.INVISIBLE
        val idSoal = HurufLevelNolFragmentArgs.fromBundle(arguments as Bundle).idSoal
        showScreen(idSoal)
        binding.submitNolHuruf.setOnClickListener{
            val bitmap = binding.cnvsLevelNolHuruf.getBitmap().scale(224, 224)
            val result = Predict.predictHuruf(activity!!, bitmap, answer!!)
            submitDrawing(idSoal, result)
            Template.saveMediaToStorage(bitmap, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")}
    }

    private fun submitDrawing(id: Int, score: Int) {
        binding.progressBarH0.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufPresenter.predictHuruf(id, score)
                    .collectLatest {
                        binding.progressBarH0.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredict(
                            context!!,
                            { view?.findNavController()?.navigate(HurufLevelNolFragmentDirections.toScoreHurufNol()) },
                            score,
                            0
                        )
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarH0.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id).collectLatest {
                    showData(it)
                    binding.progressBarH0.visibility = View.INVISIBLE
                    answer = it.jawaban[0]
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
        Template.speak(data.keterangan)
        binding.spkNolHuruf.setOnClickListener {
            Template.speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = data.keterangan
        binding.levelHurufKe.text = "Level ke ${data.idLevel}"
    }

    private fun refreshCanvasOnClick(){
        binding.refreshNolHuruf.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelNolHuruf.clearCanvas()
    }

    private fun back(){
        binding.levelNolHurufBack.setOnClickListener {
            val backSoal = HurufLevelNolFragmentDirections.actionHurufLevelNolNavToListSoalHurufNav()
            backSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backSoal)
        }
    }

}