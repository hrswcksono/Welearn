package com.tugasakhir.welearn.core.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.messaging.FirebaseMessaging

class ExitApp : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_JOIN_ANGKA)
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_JOIN_HURUF)

        stopSelf()
    }
}