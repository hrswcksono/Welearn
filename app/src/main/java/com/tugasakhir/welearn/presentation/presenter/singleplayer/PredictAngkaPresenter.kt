package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictAngkaPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun predictAngka(idSoal: Int, score: Int) =
        useCase.angkaPredict(idSoal, score)
}