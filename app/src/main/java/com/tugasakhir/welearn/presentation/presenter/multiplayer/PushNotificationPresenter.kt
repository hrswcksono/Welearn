package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase
import kotlinx.coroutines.flow.Flow

class PushNotificationPresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse> =
        useCase.pushNotification(body)
}