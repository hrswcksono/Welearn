package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class JoinGamePresenter(private val useCase: WelearnUseCase): ViewModel() {
    fun joinGame(idGame: String) = useCase.joinGame(idGame)
}