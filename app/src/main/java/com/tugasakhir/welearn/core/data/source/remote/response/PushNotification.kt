package com.tugasakhir.welearn.core.data.source.remote.response

data class PushNotification(
    val data: NotificationData,
    val to: String
)

data class NotificationData(
    val title: String,
    val message: String
)
