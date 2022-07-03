package com.tugasakhir.welearn.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.tugasakhir.welearn.R

class SharedPreference (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
        const val USER_ID = "user_id"
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

    fun deleteToken(){
        prefs.edit().remove(USER_TOKEN).apply()
    }

    fun saveUserID(userId: Int){
        val editor = prefs.edit()
        editor.putInt(USER_ID, userId)
        editor.apply()
    }

    fun fetchUserId(): Int? {
        return prefs.getInt(USER_ID, 0)
    }

    fun saveName(string: String){
        val token = prefs.edit()
        token.putString(USER_NAME, string)
        token.apply()
    }

    fun fetchName(): String? {
        return prefs.getString(USER_NAME, null)
    }
}