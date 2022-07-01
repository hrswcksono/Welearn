package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictHurufViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun predictHuruf(id_soal: Int, image: ArrayList<String>) =
        useCase.hurufPredict(id_soal, image)
}