package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class JoinGameViewModel(private val useCase: WelearnUseCase): ViewModel() {
    fun joinGame(id_game: String,token: String) = useCase.joinGame(id_game, token)
}