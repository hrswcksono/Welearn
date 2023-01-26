package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class MakeRoomPresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun makeRoom(idJenis: Int, idLevel: Int, authToken: String) = useCase.makeRoomGame(idJenis, idLevel, authToken)
}