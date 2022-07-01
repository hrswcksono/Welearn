package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictAngkaViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun predictAngka(id_soal: Int, image: ArrayList<String>) =
        useCase.angkaPredict(id_soal, image)
}