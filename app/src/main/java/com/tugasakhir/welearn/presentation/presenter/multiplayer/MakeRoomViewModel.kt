package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class MakeRoomViewModel(private val useCase: WelearnUseCase): ViewModel() {
    fun makeRoom(idJenis: Int) = useCase.makeRoomGame(idJenis)
}