package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class JoinGamePresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun joinGame(idGame: String, authToken: String) = useCase.joinGame(idGame, authToken)
}