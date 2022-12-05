package com.tugasakhir.welearn.core.utils

import android.app.Activity
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
import androidx.core.app.TaskStackBuilder
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.presentation.ui.angka.canvas.*
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelDuaActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelNolActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelSatuActivity
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelTigaActivity
import com.tugasakhir.welearn.presentation.ui.huruf.multiplayer.HurufReadyActivity
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

        var player = 0

        if (message.data["action"] == "gabung") {
            player++
            print(player)
        }

        val flags = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> FLAG_UPDATE_CURRENT or FLAG_MUTABLE
            else -> FLAG_UPDATE_CURRENT
        }

        pendingIntent1 = getActivity(this,0,intent, FLAG_MUTABLE)

        if (message.data["type"] == "angka") {
//            intent1 = Intent(this, AngkaReadyActivity::class.java)
//            intent1.putExtra(AngkaReadyActivity.ID_GAME, message.data["action"])
//            pendingIntent1 = TaskStackBuilder.create(this)
//                .addParentStack(AngkaReadyActivity::class.java)
//                .addNextIntent(intent1)
//                .getPendingIntent(110, flags)!!
            pendingIntent1 = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.bot_nav)
                .setDestination(R.id.angka_ready_nav)
                .setComponentName(MainActivity::class.java)
                .createPendingIntent()

        } else if(message.data["type"] == "huruf") {
//            intent1 = Intent(this, HurufReadyActivity::class.java)
//            intent1.putExtra(HurufReadyActivity.ID_GAME, message.data["action"])
//            pendingIntent1 = TaskStackBuilder.create(this)
//                .addParentStack(HurufReadyActivity::class.java)
//                .addNextIntent(intent1)
//                .getPendingIntent(110, flags)!!
            pendingIntent1 = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.bot_nav)
                .setDestination(R.id.huruf_ready_nav)
                .setComponentName(MainActivity::class.java)
                .createPendingIntent()
        } else if (message.data["type"] == "score"){
            intent1 = Intent(this, ScoreMultiplayerActivity::class.java)
            intent1.putExtra(ScoreMultiplayerActivity.ID_GAME, message.data["action"])
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent1)
        } else if(message.data["type"] == "startangka") {
            when(message.data["idLevel"]){
                "0" -> {
                    pendingIntent1 = startPendingIntent(
                        R.navigation.bot_nav,
                        R.id.angka_level_nol_nav,
                        bundleStart(message)
                    )
                }
                "1" -> {
                    pendingIntent1 = startPendingIntent(
                        R.navigation.bot_nav,
                        R.id.angka_level_satu_nav,
                        bundleStart(message)
                    )
                }
                "2" -> {
                    pendingIntent1 = startPendingIntent(
                        R.navigation.bot_nav,
                        R.id.angka_level_dua_nav,
                        bundleStart(message)
                    )
                }
                "3" -> {
                    pendingIntent1 = startPendingIntent(
                        R.navigation.bot_nav,
                        R.id.angka_level_tiga_nav,
                        bundleStart(message)
                    )
                }
                "4" -> {
                    pendingIntent1 = startPendingIntent(
                        R.navigation.bot_nav,
                        R.id.angka_level_empat_nav,
                        bundleStart(message)
                    )
                }
            }
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent1)
        } else if(message.data["type"] == "starthuruf") {
            when(message.data["idLevel"]){
                "0" -> {
                    intent1 = Intent(this, HurufLevelNolActivity::class.java)
                    intent1.putExtra(HurufLevelNolActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(HurufLevelNolActivity.GAME_MODE, "multi")
                    intent1.putExtra(HurufLevelNolActivity.ID_GAME, message.data["action"])
                }
                "1" -> {
                    intent1 = Intent(this, HurufLevelSatuActivity::class.java)
                    intent1.putExtra(HurufLevelSatuActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(HurufLevelSatuActivity.GAME_MODE, "multi")
                    intent1.putExtra(HurufLevelSatuActivity.ID_GAME, message.data["action"])
                }
                "2" -> {
                    intent1 = Intent(this, HurufLevelDuaActivity::class.java)
                    intent1.putExtra(HurufLevelDuaActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(HurufLevelDuaActivity.GAME_MODE, "multi")
                    intent1.putExtra(HurufLevelDuaActivity.ID_GAME, message.data["action"])
                }
                "3" -> {
                    intent1 = Intent(this, HurufLevelTigaActivity::class.java)
                    intent1.putExtra(HurufLevelTigaActivity.LEVEL_SOAL, message.data["idSoal"])
                    intent1.putExtra(HurufLevelTigaActivity.GAME_MODE, "multi")
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

    private fun bundleStart(message: RemoteMessage): Bundle {
        return bundleOf(
            "idSoal" to message.data["idSoal"],
            "mode" to "multi",
            "idGame" to message.data["action"]
        )
    }

    private fun startPendingIntent(navGraphId: Int, destId: Int, args: Bundle) : PendingIntent {
        return NavDeepLinkBuilder(this)
            .setGraph(navGraphId)
            .setDestination(destId)
            .setArguments(args)
            .setComponentName(MainActivity::class.java)
            .createPendingIntent()
    }

}