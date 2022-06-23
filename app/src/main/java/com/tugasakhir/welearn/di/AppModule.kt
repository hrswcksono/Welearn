package com.tugasakhir.welearn.di

import com.tugasakhir.welearn.domain.usecase.WelearnInteractor
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.presentation.viewmodel.multiplayer.PushNotificationStartViewModel
import com.tugasakhir.welearn.presentation.viewmodel.multiplayer.PushNotificationViewModel
import com.tugasakhir.welearn.presentation.ui.TestViewModel
import com.tugasakhir.welearn.presentation.ui.angka.PredictAngkaViewModel
import com.tugasakhir.welearn.presentation.viewmodel.multiplayer.RandomLevelAngkaViewModel
import com.tugasakhir.welearn.presentation.viewmodel.singleplayer.ListSoalAngkaViewModel
import com.tugasakhir.welearn.presentation.viewmodel.auth.LoginViewModel
import com.tugasakhir.welearn.presentation.viewmodel.auth.RegisterViewModel
import com.tugasakhir.welearn.presentation.viewmodel.auth.LogoutViewModel
import com.tugasakhir.welearn.presentation.ui.huruf.PredictHurufViewModel
import com.tugasakhir.welearn.presentation.viewmodel.multiplayer.RandomLevelHurufViewModel
import com.tugasakhir.welearn.presentation.viewmodel.singleplayer.ListSoalHurufViewModel
import com.tugasakhir.welearn.presentation.viewmodel.multiplayer.JoinGameViewModel
import com.tugasakhir.welearn.presentation.viewmodel.multiplayer.MakeRoomViewModel
import com.tugasakhir.welearn.presentation.viewmodel.ProfileViewModel
import com.tugasakhir.welearn.presentation.viewmodel.score.ScoreAngkaViewModel
import com.tugasakhir.welearn.presentation.viewmodel.score.ScoreHurufViewModel
import com.tugasakhir.welearn.presentation.viewmodel.score.UserScoreAngkaViewModel
import com.tugasakhir.welearn.presentation.viewmodel.score.UserScoreHurufViewModel
import com.tugasakhir.welearn.presentation.viewmodel.score.SoalByIDViewModel
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
}