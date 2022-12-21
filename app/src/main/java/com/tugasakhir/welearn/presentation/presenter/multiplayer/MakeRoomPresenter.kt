package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class MakeRoomPresenter(private val useCase: WelearnUseCase): ViewModel() {
    fun makeRoom(idJenis: Int, idLevel: Int) = useCase.makeRoomGame(idJenis, idLevel)
}