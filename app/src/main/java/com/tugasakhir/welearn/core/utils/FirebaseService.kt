package com.tugasakhir.welearn.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.presentation.ui.angka.multiplayer.canvas.*
import com.tugasakhir.welearn.presentation.ui.huruf.multiplayer.canvas.HurufLevelDuaActivity
import com.tugasakhir.welearn.presentation.ui.huruf.multiplayer.canvas.HurufLevelNolActivity
import com.tugasakhir.welearn.presentation.ui.huruf.multiplayer.canvas.HurufLevelSatuActivity
import com.tugasakhir.welearn.presentation.ui.huruf.multiplayer.canvas.HurufLevelTigaActivity
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreMultiplayerActivity
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

        val flags = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> FLAG_UPDATE_CURRENT or FLAG_MUTABLE
            else -> FLAG_UPDATE_CURRENT
        }

        pendingIntent1 = getActivity(this,0,intent, FLAG_MUTABLE)

        if (message.data["type"] == "angka") {
            val bundle = bundleOf("idGame" to message.data["action"])
            pendingIntent1 = startPendingIntent(R.id.angka_ready_nav, bundle)
        } else if(message.data["type"] == "huruf") {
            val bundle = bundleOf("idGame" to message.data["action"])
            pendingIntent1 = startPendingIntent(R.id.huruf_ready_nav, bundle)
        } else if (message.data["type"] == "score"){
            intent1 = Intent(this, ScoreMultiplayerActivity::class.java)
            intent1.putExtra(ScoreMultiplayerActivity.ID_GAME, message.data["action"])
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent1)
        } else if(message.data["type"] == "startangka") {
            when(message.data["idLevel"]){
                "0" -> {
                    intent1 = Intent(this, AngkaLevelNolActivity::class.java)
                    intent1.putExtra(AngkaLevelNolActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(AngkaLevelNolActivity.ID_GAME, message.data["action"])
                }
                "1" -> {
                    intent1 = Intent(this, AngkaLevelSatuActivity::class.java)
                    intent1.putExtra(AngkaLevelSatuActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(AngkaLevelSatuActivity.ID_GAME, message.data["action"])
                }
                "2" -> {
                    intent1 = Intent(this, AngkaLevelDuaActivity::class.java)
                    intent1.putExtra(AngkaLevelDuaActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(AngkaLevelDuaActivity.ID_GAME, message.data["action"])
                }
                "3" -> {
                    intent1 = Intent(this, AngkaLevelTigaActivity::class.java)
                    intent1.putExtra(AngkaLevelTigaActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(AngkaLevelTigaActivity.ID_GAME, message.data["action"])
                }
                "4" -> {
                    intent1 = Intent(this, AngkaLevelEmpatActivity::class.java)
                    intent1.putExtra(AngkaLevelEmpatActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(AngkaLevelEmpatActivity.ID_GAME, message.data["action"])
                }
            }
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent1)
        } else if(message.data["type"] == "starthuruf") {
            when(message.data["idLevel"]){
                "0" -> {
                    intent1 = Intent(this, HurufLevelNolActivity::class.java)
                    intent1.putExtra(HurufLevelNolActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(HurufLevelNolActivity.ID_GAME, message.data["action"])
                }
                "1" -> {
                    intent1 = Intent(this, HurufLevelSatuActivity::class.java)
                    intent1.putExtra(HurufLevelSatuActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(HurufLevelSatuActivity.ID_GAME, message.data["action"])
                }
                "2" -> {
                    intent1 = Intent(this, HurufLevelDuaActivity::class.java)
                    intent1.putExtra(HurufLevelDuaActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(HurufLevelDuaActivity.ID_GAME, message.data["action"])
                }
                "3" -> {
                    intent1 = Intent(this, HurufLevelTigaActivity::class.java)
                    intent1.putExtra(HurufLevelTigaActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(HurufLevelTigaActivity.ID_GAME, message.data["action"])
                }
            }
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent1)
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

    private fun startPendingIntent(destId: Int, args: Bundle) : PendingIntent {
        return NavDeepLinkBuilder(this)
            .setGraph(R.navigation.bot_nav)
            .setDestination(destId)
            .setArguments(args)
            .setComponentName(MainActivity::class.java)
            .createPendingIntent()
    }

}