package com.tugasakhir.welearn.domain.entity

data class PushNotification(
    val data: NotificationData,
    val to: String,
    val priority: String
)

data class NotificationData(
    val title: String,
    val message: String,
    val type: String,
    val idSoal: String,
    val idLevel: Int,
    val action: String,
)