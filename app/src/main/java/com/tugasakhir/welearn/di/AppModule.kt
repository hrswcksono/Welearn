package com.tugasakhir.welearn.di

import com.tugasakhir.welearn.data.UserRepository
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerInteractor
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase
import com.tugasakhir.welearn.domain.usecase.score.ScoreInteractor
import com.tugasakhir.welearn.domain.usecase.score.ScoreUseCase
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerInteractor
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerUseCase
import com.tugasakhir.welearn.domain.usecase.soal.SoalInteractor
import com.tugasakhir.welearn.domain.usecase.soal.SoalUseCase
import com.tugasakhir.welearn.domain.usecase.user.UserInteractor
import com.tugasakhir.welearn.domain.usecase.user.UserUseCase
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import com.tugasakhir.welearn.presentation.presenter.soal.ListSoalRandomPresenter
import com.tugasakhir.welearn.presentation.presenter.user.LoginPresenter
import com.tugasakhir.welearn.presentation.presenter.user.RegisterPresenter
import com.tugasakhir.welearn.presentation.presenter.user.LogoutPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import com.tugasakhir.welearn.presentation.presenter.user.ProfilePresenter
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.score.*
import com.tugasakhir.welearn.presentation.presenter.singleplayer.LevelSoalPresenter
import com.tugasakhir.welearn.presentation.presenter.soal.RandomIDSoalMultiPresenter
import com.tugasakhir.welearn.presentation.presenter.soal.SoalByIDPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
    factory<MultiPlayerUseCase> { MultiPlayerInteractor(get()) }
    factory<SinglePlayerUseCase> { SinglePlayerInteractor(get()) }
    factory<SoalUseCase> { SoalInteractor(get()) }
    factory<ScoreUseCase> { ScoreInteractor(get()) }
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
