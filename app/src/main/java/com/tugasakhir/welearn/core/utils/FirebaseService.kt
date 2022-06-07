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
import com.tugasakhir.welearn.presentation.ui.angka.canvas.*
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

        var player = 0

        if (message.data["action"] == "gabung") {
            player++
            print(player)
        }

        pendingIntent1 = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT)

        if (message.data["type"] == "angka") {
            intent1 = Intent(this, AngkaReadyActivity::class.java)
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
            if (message.data["id_level"] == "0") {
                intent1 = Intent(this, AngkaLevelNolActivity::class.java)
                intent1.putExtra(AngkaLevelNolActivity.LEVEL_SOAL, message.data["id_soal"])
                intent1.putExtra(AngkaLevelNolActivity.GAME_MODE, "multi")
            } else if (message.data["id_level"] == "1") {
                intent1 = Intent(this, AngkaLevelSatuActivity::class.java)
                intent1.putExtra(AngkaLevelSatuActivity.EXTRA_SOAL, message.data["id_soal"]!!.toInt())
//                intent1.putExtra(AngkaLevelSatuActivity.EXTRA_SOAL, message.data["id_soal"]!!.toInt())
            } else if (message.data["id_level"] == "2") {
                intent1 = Intent(this, AngkaLevelDuaActivity::class.java)
                intent1.putExtra(AngkaLevelDuaActivity.EXTRA_SOAL, message.data["id_soal"]!!.toInt())
            } else if (message.data["id_level"] == "3") {
                intent1 = Intent(this, AngkaLevelTigaActivity::class.java)
                intent1.putExtra(AngkaLevelTigaActivity.EXTRA_SOAL, message.data["id_soal"]!!.toInt())
            } else if (message.data["id_level"] == "4") {
                intent1 = Intent(this, AngkaLevelEmpatActivity::class.java)
                intent1.putExtra(AngkaLevelEmpatActivity.EXTRA_SOAL, message.data["id_soal"]!!.toInt())
            }
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent1)
        } else if(message.data["type"] == "starthuruf") {
            intent1 = Intent(this, HurufReadyActivity::class.java)
//            intent1.putExtra(HurufReadyActivity.LEVEL_HURUF, )
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