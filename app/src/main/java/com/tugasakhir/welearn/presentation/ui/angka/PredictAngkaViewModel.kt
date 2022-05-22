package com.tugasakhir.welearn.presentation.ui.angka

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictAngkaViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun predictAngka(id_soal: String, image: ArrayList<String>, token: String) =
        useCase.angkaPredict(id_soal, image, token)
}