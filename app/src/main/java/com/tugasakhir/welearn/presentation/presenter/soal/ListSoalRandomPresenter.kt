package com.tugasakhir.welearn.presentation.presenter.soal

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.soal.SoalUseCase

class ListSoalRandomPresenter(private val useCase: SoalUseCase): ViewModel() {

    fun randomSoalSingle(jenis :Int, level:Int) = useCase.getSoalRandomSinglePlayer(jenis, level)

}