package com.tugasakhir.welearn.presentation.view.angka.singleplayer.canvas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.utils.*
import com.tugasakhir.welearn.utils.Template
import com.tugasakhir.welearn.databinding.FragmentAngkaLevelNolBinding
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.InGamePresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AngkaLevelNolFragment : Fragment() {

    private var _binding: FragmentAngkaLevelNolBinding ?= null
    private val binding get() = _binding!!
    private val soalViewModel: InGamePresenter by viewModel()
    private val predictAngkaPresenter: PredictAngkaPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: Char ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAngkaLevelNolBinding.inflate(inflater, container, false)
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
        binding.cnvsLevelNolAngka.setStrokeWidth(30f)
    }

    private fun main() {
        binding.btnUserParticipantA0.visibility = View.INVISIBLE
        val idSoal = AngkaLevelNolFragmentArgs.fromBundle(arguments as Bundle).idSoal
        showScreen(idSoal)
        binding.submitNolAngka.setOnClickListener{
            val canvas1 = binding.cnvsLevelNolAngka.getBitmap().scale(240,240)
            val (result, accuracy) = Predict.PredictAngkaCoba(activity!!, canvas1)
            var score = 0
            if (result == answer){
                score = 10
            }
            submitDrawing(idSoal, score, dialogText(result, accuracy))
        }
    }

    private fun dialogText(answer: Char, accuracy: Float) : String {
        return "Jawaban kamu $answer\n Ketelitian ${(accuracy*100).toInt()}%"
    }

    private fun disableButton(){
        binding.submitNolAngka.isEnabled = false
        binding.submitNolAngka.isClickable = false
    }

    private fun enableButton(){
        binding.submitNolAngka.isEnabled = true
        binding.submitNolAngka.isClickable = true
    }

    private fun showScreen(id: Int) {
        binding.progressBarA0.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id, sessionManager.fetchAuthToken()!!).collectLatest {
                    when(it) {
                        is Resource.Success ->{
                            showData(it.data!!)
                            answer = it.data.jawaban[0]
                            binding.progressBarA0.visibility = View.INVISIBLE
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
        binding.spkNolAngka.setOnClickListener {
            Template.speak(data.keterangan + " " + data.soal)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoal.text = data.soal
    }

    private fun submitDrawing(id: Int, score: Int, message: String) {
        binding.progressBarA0.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id, score, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        binding.progressBarA0.visibility = View.INVISIBLE
                        CustomDialogBox.dialogPredictCoba(
                            context!!,
                            { view?.findNavController()?.navigate(AngkaLevelNolFragmentDirections.toScoreAngkaNol()) },
                            score,
                            message
                        )
                    }
            }
        }
    }

    private fun refreshCanvasOnClick(){
        binding.refreshNolAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelNolAngka.clearCanvas()
    }

    private fun back(){
        binding.levelNolAngkaBack.setOnClickListener {
            val backSoal = AngkaLevelNolFragmentDirections.actionAngkaLevelNolNavToListSoalAngkaNav()
            backSoal.idLevel = sessionManager.getIDLevel()!!
            view?.findNavController()?.navigate(backSoal)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}