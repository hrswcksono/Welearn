package com.tugasakhir.welearn.core.utils

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import com.google.firebase.messaging.FirebaseMessaging

class ExitApp : Service() {
    companion object {
        var sharedPref: SharedPreferences? = null

        var topic: String?
            get() {
                return sharedPref?.getString("token", "")
            }
            set(value) {
                sharedPref?.edit()?.putString("token", value)?.apply()
            }
    }
    
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic!!)
        stopSelf()
    }
}