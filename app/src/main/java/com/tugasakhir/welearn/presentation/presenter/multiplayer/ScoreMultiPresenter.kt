package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class ScoreMultiPresenter(private val useCase: MultiPlayerUseCase): ViewModel()  {
    fun scoreMulti(idGame: Int) = useCase.scoreMulti(idGame)
}