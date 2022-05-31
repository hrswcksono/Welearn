package com.tugasakhir.welearn.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.multiplayer.AngkaReadyActivity
import com.tugasakhir.welearn.presentation.ui.huruf.multiplayer.HurufReadyActivity
import kotlin.random.Random

private const val CHANNEL_ID = "my_channel"

class FirebaseService : FirebaseMessagingService() {

    private lateinit var pendingIntent1: PendingIntent
    private lateinit var intent1: Intent

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val intent = Intent(this, MainActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        pendingIntent1 = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT)

        if (message.data["type"] == "angka") {
            intent1 = Intent(this, AngkaReadyActivity::class.java)
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent1)
            pendingIntent1 = TaskStackBuilder.create(this)
                .addParentStack(AngkaReadyActivity::class.java)
                .addNextIntent(intent1)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)!!
        } else if(message.data["type"] == "huruf") {
            intent1 = Intent(this, HurufReadyActivity::class.java)
            pendingIntent1 = TaskStackBuilder.create(this)
                .addParentStack(HurufReadyActivity::class.java)
                .addNextIntent(intent1)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)!!
        } else if(message.data["type"] == "startangka") {
            intent1 = Intent(this, AngkaReadyActivity::class.java)
            intent1.putExtra(AngkaReadyActivity.LEVEL_ANGKA, mapReadyToGame(message))
//            startActivity(intent1)
            pendingIntent1 = TaskStackBuilder.create(this)
                .addParentStack(AngkaReadyActivity::class.java)
                .addNextIntent(intent1)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)!!
        } else if(message.data["type"] == "starthuruf") {
            intent1 = Intent(this, HurufReadyActivity::class.java)
            intent1.putExtra(HurufReadyActivity.LEVEL_HURUF, mapReadyToGame(message))
            pendingIntent1 = TaskStackBuilder.create(this)
                .addParentStack(HurufReadyActivity::class.java)
                .addNextIntent(intent1)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)!!
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["message"])
                .setSmallIcon(R.drawable.ic_reaction)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent1)
                .build()

        notificationManager.notify(notificationID, notification)
    }

    private fun mapReadyToGame(message: RemoteMessage) = Soal(
        id_soal = message.data["id_soal"]!!.toInt(),
        id_jenis_soal = message.data["id_jenis_soal"]!!.toInt(),
        id_level = message.data["id_level"]!!.toInt(),
        soal = message.data["soal"].toString(),
        keterangan = message.data["keterangan"].toString(),
        jawaban = message.data["jawaban"].toString()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }
}