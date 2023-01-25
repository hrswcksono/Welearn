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
    viewModel { LoginPresenter(get()) }
    viewModel { ProfilePresenter(get()) }
    viewModel { ListSoalRandomPresenter(get()) }
    viewModel { LogoutPresenter(get()) }
    viewModel { RegisterPresenter(get()) }
    viewModel { HighScorePresenter(get()) }
    viewModel { PushNotificationPresenter(get()) }
    viewModel { RandomIDSoalMultiPresenter(get()) }
    viewModel { PredictAngkaPresenter(get()) }
    viewModel { PredictHurufPresenter(get()) }
    viewModel { ScoreUserPresenter(get()) }
    viewModel { MakeRoomPresenter(get()) }
    viewModel { JoinGamePresenter(get()) }
    viewModel { SoalByIDPresenter(get()) }
    viewModel { JoinedUserPresenter(get()) }
    viewModel { ScoreMultiPresenter(get()) }
    viewModel { LevelSoalPresenter(get()) }
    viewModel { PredictHurufMultiPresenter(get()) }
    viewModel { PredictAngkaMultiPresenter(get()) }
    viewModel { EndGamePresenter(get()) }
    viewModel { UserParticipantPresenter(get()) }
}
