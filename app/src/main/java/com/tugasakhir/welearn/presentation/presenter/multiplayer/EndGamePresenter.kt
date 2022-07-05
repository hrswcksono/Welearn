package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class EndGamePresenter(private val useCase: WelearnUseCase): ViewModel() {
    fun endGame(idGame: String) = useCase.endGame(idGame)
}