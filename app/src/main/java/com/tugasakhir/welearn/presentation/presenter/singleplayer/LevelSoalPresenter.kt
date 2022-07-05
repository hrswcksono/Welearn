package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class LevelSoalPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun getLevelSoal(idLevel: Int) = useCase.getLevel(idLevel)
}