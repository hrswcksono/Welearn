package com.tugasakhir.welearn.di

import com.tugasakhir.welearn.domain.usecase.WelearnInteractor
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.presentation.ui.auth.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<WelearnUseCase> { WelearnInteractor(get())}
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}