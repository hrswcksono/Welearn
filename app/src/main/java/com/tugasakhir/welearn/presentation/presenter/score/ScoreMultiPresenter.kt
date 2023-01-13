package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.score.ScoreUseCase

class ScoreMultiPresenter(private val useCase: ScoreUseCase): ViewModel()  {

    fun scoreMulti(idGame: Int) = useCase.scoreMulti(idGame)
}