package com.tugasakhir.welearn.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.tugasakhir.welearn.R

class SharedPreference (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val SUBSCRIBE_FCM = "subscribe_fcm"
        const val SUBSCRIBE_NEW_FCM = "subscribe_new_fcm"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun newTokenFCM(string: String){
        val token = prefs.edit()
        token.putString(SUBSCRIBE_NEW_FCM, string)
        token.apply()
    }

    fun fetchTokenFCM(): String? {
        return prefs.getString(SUBSCRIBE_NEW_FCM, null)
    }

    fun deleteToken(){
        prefs.edit().remove(USER_TOKEN).apply()
    }
}