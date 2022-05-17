package com.tugasakhir.welearn.presentation.ui

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.core.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class PushNotificationViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse> =
        useCase.pushNotification(body)

}