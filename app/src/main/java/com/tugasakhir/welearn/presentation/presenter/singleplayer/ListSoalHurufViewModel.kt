package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class ListSoalHurufViewModel(private val useCase: WelearnUseCase): ViewModel() {
    fun randomHuruf(level: Int) = useCase.hurufRandom(level)
}