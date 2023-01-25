package com.tugasakhir.welearn.presentation.ui.angka.singleplayer.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.core.utils.*
import com.tugasakhir.welearn.databinding.FragmentAngkaLevelTigaBinding
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.multiplayer.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AngkaLevelTigaFragment : Fragment() {

    private var _binding: FragmentAngkaLevelTigaBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictAngkaPresenter: PredictAngkaPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: Char ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAngkaLevelTigaBinding.inflate(inflater, container, false)
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
        binding.cnvsLevelTigaAngka.setStrokeWidth(30f)
    }

    private fun main() {
        binding.btnUserParticipantA3.visibility = View.INVISIBLE
        val idSoal = AngkaLevelNolFragmentArgs.fromBundle(arguments as Bundle).idSoal
        showScreen(idSoal)
        binding.submitTigaAngka.setOnClickListener{
            val bitmap = binding.cnvsLevelTigaAngka.getBitmap().scale(224, 224)
            val result = Predict.PredictAngka(activity!!, bitmap, answer!!)
            submitDrawing(idSoal, result)
            Template.saveMediaToStorage(bitmap, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
        }
    }

    private fun disableButton(){
        binding.submitTigaAngka.isEnabled = false
        binding.submitTigaAngka.isClickable = false
    }

    private fun enableButton(){
        binding.submitTigaAngka.isEnabled = true
        binding.submitTigaAngka.isClickable = true
    }

    private fun submitDrawing(id: Int, score: Int) {
        binding.progressBarA3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id, score)
                    .collectLatest {
                        binding.progressBarA3.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredict(
                            context!!,
                            { view?.findNavController()?.navigate(AngkaLevelTigaFragmentDirections.toScoreAngkaTiga()) },
                            score,
                        )
                    }
            }
        }
    }

    private fun showScreen(idSoal: Int) {
        binding.progressBarA3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(idSoal).collectLatest {
                    showData(it)
                    answer = it.jawaban[0]
                    binding.progressBarA3.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
        Template.speak(data.keterangan + " " + data.soal)
        binding.spkTigaAngka.setOnClickListener {
            Template.speak(data.keterangan + " " + data.soal)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoalAngkaTiga.text = data.soal
    }

    private fun refreshCanvasOnClick(){
        binding.refreshTigaAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelTigaAngka.clearCanvas()
    }

    private fun back(){
        binding.levelTigaAngkaBack.setOnClickListener {
            val backSoal = AngkaLevelTigaFragmentDirections.actionAngkaLevelTigaNavToListSoalAngkaNav()
            backSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backSoal)
        }
    }
}