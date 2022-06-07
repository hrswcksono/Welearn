package com.tugasakhir.welearn.domain.model

data class PushNotification(
    val data: NotificationData,
    val to: String,
    val priority: String
)

data class NotificationData(
    val title: String,
    val message: String,
    val type: String
)

data class PushNotificationStart(
    val data: StartGame,
    val to: String,
    val priority: String
)

data class StartGame(
    val title: String,
    val message: String,
    val type: String,
    val id_soal: String,
    val id_level: Int,
    val action: String
)