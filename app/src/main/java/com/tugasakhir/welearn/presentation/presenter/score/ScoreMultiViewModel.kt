package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class ScoreMultiViewModel(private val useCase: WelearnUseCase): ViewModel()  {

    fun scoreMulti(idGame: Int) =
        useCase.scoreMulti(idGame)
}