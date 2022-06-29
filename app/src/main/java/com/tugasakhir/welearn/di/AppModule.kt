package com.tugasakhir.welearn.di

import com.tugasakhir.welearn.domain.usecase.WelearnInteractor
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.presentation.ui.TestViewModel
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaViewModel
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ListSoalAngkaViewModel
import com.tugasakhir.welearn.presentation.presenter.auth.LoginViewModel
import com.tugasakhir.welearn.presentation.presenter.auth.RegisterViewModel
import com.tugasakhir.welearn.presentation.presenter.auth.LogoutViewModel
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufViewModel
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ListSoalHurufViewModel
import com.tugasakhir.welearn.presentation.presenter.ProfileViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.score.*
import com.tugasakhir.welearn.presentation.presenter.singleplayer.LevelSoalViewModel
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
    viewModel { PushNotificationViewModel(get()) }
    viewModel { PushNotificationStartViewModel(get()) }
    viewModel { RandomLevelAngkaViewModel(get()) }
    viewModel { RandomLevelHurufViewModel(get()) }
    viewModel { PredictAngkaViewModel(get()) }
    viewModel { PredictHurufViewModel(get()) }
    viewModel { TestViewModel(get()) }
    viewModel { UserScoreAngkaViewModel(get()) }
    viewModel { UserScoreHurufViewModel(get()) }
    viewModel { MakeRoomViewModel(get()) }
    viewModel { JoinGameViewModel(get()) }
    viewModel { SoalByIDViewModel(get()) }
    viewModel { JoinedUserViewModel(get()) }
    viewModel { ScoreMultiViewModel(get()) }
    viewModel { LevelSoalViewModel(get()) }
    viewModel { PredictHurufMultiViewModel(get()) }
}