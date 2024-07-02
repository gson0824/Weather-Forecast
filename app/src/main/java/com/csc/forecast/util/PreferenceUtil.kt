package com.csc.forecast.util

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtil {
    private const val PREF_NAME = "forecast_prefs"
    private const val KEY_READ_FROM_FILE = "read_from_file"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveReadFromFile(context: Context, readFromFile: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(KEY_READ_FROM_FILE, readFromFile)
        editor.apply()
    }

    fun loadReadFromFile(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_READ_FROM_FILE, false)
    }
}