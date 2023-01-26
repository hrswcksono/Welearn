package com.tugasakhir.welearn.di

import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerInteractor
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerInteractor
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerUseCase
import com.tugasakhir.welearn.domain.usecase.auth.AuthInteractor
import com.tugasakhir.welearn.domain.usecase.auth.AuthUseCase
import com.tugasakhir.welearn.presentation.presenter.user.LoginPresenter
import com.tugasakhir.welearn.presentation.presenter.user.RegisterPresenter
import com.tugasakhir.welearn.presentation.presenter.user.LogoutPresenter
import com.tugasakhir.welearn.presentation.presenter.user.ProfilePresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.multiplayer.RandomIDSoalMultiPresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<AuthUseCase> { AuthInteractor(get()) }
    factory<MultiPlayerUseCase> { MultiPlayerInteractor(get()) }
    factory<SinglePlayerUseCase> { SinglePlayerInteractor(get()) }
}

val presentationModule = module {
    viewModel { LoginPresenter(get<AuthUseCase>()) }
    viewModel { ProfilePresenter(get<AuthUseCase>()) }
    viewModel { LogoutPresenter(get<AuthUseCase>()) }
    viewModel { RegisterPresenter(get<AuthUseCase>()) }
    viewModel { LevelSoalPresenter(get<SinglePlayerUseCase>()) }
    viewModel { ListSoalRandomPresenter(get<SinglePlayerUseCase>()) }
    viewModel { HighScorePresenter(get<SinglePlayerUseCase>()) }
    viewModel { PredictAngkaPresenter(get<SinglePlayerUseCase>()) }
    viewModel { PredictHurufPresenter(get<SinglePlayerUseCase>()) }
    viewModel { ScoreUserPresenter(get<SinglePlayerUseCase>()) }
    viewModel { PushNotificationPresenter(get<MultiPlayerUseCase>()) }
    viewModel { RandomIDSoalMultiPresenter(get<MultiPlayerUseCase>()) }
    viewModel { MakeRoomPresenter(get<MultiPlayerUseCase>()) }
    viewModel { JoinGamePresenter(get<MultiPlayerUseCase>()) }
    viewModel { SoalByIDPresenter(get<MultiPlayerUseCase>()) }
    viewModel { JoinedUserPresenter(get<MultiPlayerUseCase>()) }
    viewModel { ScoreMultiPresenter(get<MultiPlayerUseCase>()) }
    viewModel { PredictHurufMultiPresenter(get<MultiPlayerUseCase>()) }
    viewModel { PredictAngkaMultiPresenter(get<MultiPlayerUseCase>()) }
    viewModel { EndGamePresenter(get<MultiPlayerUseCase>()) }
    viewModel { UserParticipantPresenter(get<MultiPlayerUseCase>()) }
}
