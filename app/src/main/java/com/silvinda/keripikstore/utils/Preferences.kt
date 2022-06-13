package com.silvinda.keripikstore.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {
    companion object {
        const val MEETING_PREF = "USER_PREF"
    }

    private val sharedPref = context.getSharedPreferences(MEETING_PREF, 0)

    fun setValues(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun  getValues(key: String): String?{
        return sharedPref.getString(key, "")
    }
}