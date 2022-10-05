package com.tugasakhir.welearn.di

import com.tugasakhir.welearn.domain.usecase.WelearnInteractor
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ListSoalRandomPresenter
import com.tugasakhir.welearn.presentation.presenter.auth.LoginPresenter
import com.tugasakhir.welearn.presentation.presenter.auth.RegisterPresenter
import com.tugasakhir.welearn.presentation.presenter.auth.LogoutPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import com.tugasakhir.welearn.presentation.presenter.ProfilePresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.score.*
import com.tugasakhir.welearn.presentation.presenter.singleplayer.LevelSoalPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<WelearnUseCase> { WelearnInteractor(get())}
}

val viewModelModule = module {
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