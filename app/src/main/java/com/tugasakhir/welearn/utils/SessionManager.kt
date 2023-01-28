package com.tugasakhir.welearn.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    companion object {
        const val KEY_LOGIN = "isLogin"
    }

    private var pref: SharedPreferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()


    fun logout() {
        editor.clear()
        editor.commit()
    }

    fun saveToPreference(key: String, value: String) = editor.putString(key, value).commit()

    fun getFromPreference(key: String) = pref.getString(key, "")

}