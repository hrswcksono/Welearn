package com.tugasakhir.welearn.di

import com.tugasakhir.welearn.domain.usecase.WelearnInteractor
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.presentation.ui.angka.singleplayer.soal.ListSoalAngkaViewModel
import com.tugasakhir.welearn.presentation.ui.auth.login.LoginViewModel
import com.tugasakhir.welearn.presentation.ui.auth.register.RegisterViewModel
import com.tugasakhir.welearn.presentation.ui.home.LogoutViewModel
import com.tugasakhir.welearn.presentation.ui.huruf.singleplayer.soal.ListSoalHurufViewModel
import com.tugasakhir.welearn.presentation.ui.profile.ProfileViewModel
import com.tugasakhir.welearn.presentation.ui.score.ScoreAngkaViewModel
import com.tugasakhir.welearn.presentation.ui.score.ScoreHurufViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<WelearnUseCase> { WelearnInteractor(get())}
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { ListSoalAngkaViewModel(get()) }
    viewModel { ListSoalHurufViewModel(get()) }
    viewModel { LogoutViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ScoreAngkaViewModel(get()) }
    viewModel { ScoreHurufViewModel(get()) }
}