package com.tugasakhir.welearn.presentation.presenter.soal

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.soal.SoalUseCase

class SoalByIDPresenter (private val useCase: SoalUseCase): ViewModel() {
    fun getSoalByID(id: Int) = useCase.getSoalByID(id)
}