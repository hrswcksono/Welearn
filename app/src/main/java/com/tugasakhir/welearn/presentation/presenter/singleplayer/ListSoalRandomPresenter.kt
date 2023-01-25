package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerUseCase

class ListSoalRandomPresenter(private val useCase: SinglePlayerUseCase): ViewModel() {
    fun randomSoalSingle(jenis :Int, level:Int) = useCase.getSoalRandomSinglePlayer(jenis, level)
}