package com.tugasakhir.welearn.presentation.viewmodel.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.PushNotificationStart
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PushNotificationStartViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun pushNotification(body: PushNotificationStart) =
        useCase.pushNotificationStart(body)

}