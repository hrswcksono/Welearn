package com.tugasakhir.welearn.presentation.ui.huruf

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictHurufViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun predictHuruf(id_soal: String, image: ArrayList<String>, token: String) =
        useCase.hurufPredict(id_soal, image, token)
}