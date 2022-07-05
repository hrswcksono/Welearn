package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictAngkaPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun predictAngka(idSoal: Int, image: ArrayList<String>) =
        useCase.angkaPredict(idSoal, image)
}