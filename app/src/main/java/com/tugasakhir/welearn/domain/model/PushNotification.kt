package com.tugasakhir.welearn.domain.model

data class PushNotification(
    val data: NotificationData,
    val to: String
)

data class NotificationData(
    val title: String,
    val message: String,
    val type: String
)

data class PushNotificationStart(
    val data: StartGame,
    val to: String
)

data class StartGame(
    val title: String,
    val message: String,
    val type: String,
    val id_soal: Int,
    val id_level: Int
)