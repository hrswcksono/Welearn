package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class JoinGamePresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun joinGame(idRoom: Int, authToken: String) = useCase.joinGame(idRoom, authToken)
}