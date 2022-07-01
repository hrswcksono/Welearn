package com.tugasakhir.welearn.presentation.ui

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class TestViewModel(private val useCase: WelearnUseCase): ViewModel()  {
    fun testPredict(input: String) = useCase.testPredict(input)
}