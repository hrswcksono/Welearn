package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class ListSoalRandomPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun randomSoalSingle(jenis :Int, level:Int) = useCase.getSoalRandomSinglePlayer(jenis, level)

}