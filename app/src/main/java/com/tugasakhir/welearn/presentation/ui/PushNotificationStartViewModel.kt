package com.tugasakhir.welearn.presentation.ui

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.core.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.domain.model.PushNotificationStart
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class PushNotificationStartViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun pushNotification(body: PushNotificationStart) =
        useCase.pushNotificationStart(body)

}