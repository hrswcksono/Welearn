package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerUseCase

class ListSoalRandomPresenter(private val useCase: SinglePlayerUseCase): ViewModel() {
    fun randomSoalSingle(jenis :Int, level:Int, authToken: String) = useCase.getSoalRandomSinglePlayer(jenis, level, authToken)
}