package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class ForceEndGamePresenter(private val useCase: MultiPlayerUseCase): ViewModel(){
    fun forcedEndGame(idGame: String, authToken: String) = useCase.forcedEndGame(idGame, authToken)
}