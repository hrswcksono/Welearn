package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerUseCase

class PredictHurufPresenter(private val useCase: SinglePlayerUseCase): ViewModel() {

    fun predictHuruf(idSoal: Int, score: Int) = useCase.hurufPredict(idSoal, score)
}