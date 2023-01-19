package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictHurufPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun predictHuruf(idSoal: Int, score: Int) = useCase.hurufPredict(idSoal, score)
}