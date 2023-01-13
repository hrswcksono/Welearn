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
import com.tugasakhir.welearn.databinding.FragmentAngkaLevelDuaBinding
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.soal.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AngkaLevelDuaFragment : Fragment() {

    private var _binding: FragmentAngkaLevelDuaBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictAngkaPresenter: PredictAngkaPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: Char ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAngkaLevelDuaBinding.inflate(inflater, container, false)
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
        binding.cnvsLevelDuaAngka.setStrokeWidth(30f)
    }

    private fun main() {
        binding.btnUserParticipantA2.visibility = View.INVISIBLE
        val idSoal = AngkaLevelDuaFragmentArgs.fromBundle(arguments as Bundle).idSoal
        showScreen(idSoal)
        binding.submitDuaAngka.setOnClickListener{
            val bitmap = binding.cnvsLevelDuaAngka.getBitmap().scale(224, 224)
            val result = Predict.PredictAngka(activity!!, bitmap, answer!!)
            submitDrawing(idSoal, result)
            Template.saveMediaToStorage(bitmap, context!!, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
        }
    }

    private fun disableButton(){
        binding.submitDuaAngka.isEnabled = false
        binding.submitDuaAngka.isClickable = false
    }

    private fun enableButton(){
        binding.submitDuaAngka.isEnabled = true
        binding.submitDuaAngka.isClickable = true
    }

    private fun submitDrawing(id: Int, score: Int) {
        binding.progressBarA2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id, score)
                    .collectLatest {
                        binding.progressBarA2.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredict(
                            context!!,
                            { view?.findNavController()?.navigate(AngkaLevelDuaFragmentDirections.toScoreAngkaDua()) },
                            score,
                        )
                    }
            }
        }
    }

    private fun showScreen(id: Int) {
        binding.progressBarA2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id).collectLatest {
                    showData(it)
                    answer = it.jawaban[0]
                    binding.progressBarA2.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
        Template.speak(data.keterangan + " " + data.soal)
        binding.spkDuaAngka.setOnClickListener {
            Template.speak(data.keterangan + " " + data.soal)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoalAngkaDua.text = data.soal
    }

    private fun refreshCanvasOnClick(){
        binding.refreshDuaAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelDuaAngka.clearCanvas()
    }

    private fun back() {
        binding.levelDuaAngkaBack.setOnClickListener {
            val backSoal = AngkaLevelDuaFragmentDirections.actionAngkaLevelDuaNavToListSoalAngkaNav()
            backSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backSoal)
        }
    }


}