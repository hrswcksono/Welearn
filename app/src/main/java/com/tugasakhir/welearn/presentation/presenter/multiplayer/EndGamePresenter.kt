package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class EndGamePresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun endGame(idGame: String) = useCase.endGame(idGame)
}