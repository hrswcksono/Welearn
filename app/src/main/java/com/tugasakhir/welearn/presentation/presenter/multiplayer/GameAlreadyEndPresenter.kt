package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class GameAlreadyEndPresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun gameAlreadyEnd(idGame: String, authToken: String) = useCase.gameAlreadyEnd(idGame, authToken)
}