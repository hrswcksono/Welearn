package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class ListSoalAngkaViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun randomAngka(level:Int) = useCase.angkaRandom(level)
}