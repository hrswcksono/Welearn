package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerUseCase

class PredictAngkaPresenter(private val useCase: SinglePlayerUseCase): ViewModel() {

    fun predictAngka(idSoal: Int, score: Int, authToken: String) = useCase.angkaPredict(idSoal, score, authToken)
}