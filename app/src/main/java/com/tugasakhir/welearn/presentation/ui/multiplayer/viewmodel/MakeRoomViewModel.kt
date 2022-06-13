package com.tugasakhir.welearn.presentation.ui.multiplayer.viewmodel

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class MakeRoomViewModel(private val useCase: WelearnUseCase): ViewModel() {
    fun makeRoom(token: String) = useCase.makeRoomGame(token)
}