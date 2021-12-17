package com.example.up_down_android.util

import android.content.Context
import org.json.JSONArray

class PrefUtil(private val context: Context) {

    companion object {
        const val USER_ACCESS_TOKEN = "user_access_token"
    }

    fun setUserAccessToken(token: String?) {
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString(USER_ACCESS_TOKEN, token)
        editor.apply()
    }

    fun getUserAccessToken(): String? {
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return pref.getString(USER_ACCESS_TOKEN, null)
    }

    fun logout() {
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString(USER_ACCESS_TOKEN, null)

        editor.apply()
    }
}