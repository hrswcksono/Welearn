package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class MakeRoomPresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun makeRoom(idJenis: Int, idLevel: Int, authToken: String) = useCase.makeRoom(idJenis, idLevel, authToken)
    fun randomIDSoalMultiByLevel(jenis: Int, level: Int, authToken: String) =
        useCase.getIDSoalMulti(jenis, level, authToken)
}