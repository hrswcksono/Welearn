package com.tugasakhir.welearn.presentation.presenter.soal

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.soal.SoalUseCase

class RandomIDSoalMultiPresenter(private val useCase: SoalUseCase): ViewModel() {

    fun randomIDSoalMultiByLevel(jenis: Int, level: Int) =
        useCase.getIDSoalMultiplayer(jenis, level)

}