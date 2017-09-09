package uk.panasys.phonehabits.utils

import android.content.Context
import android.preference.PreferenceManager

object SharedPrefUtils {

    fun <T> setPreference(context: Context, key: String, value: T): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        when (value) {
            is String -> editor.putString(key, value as String)
            is Float -> editor.putFloat(key, value as Float)
            is Int -> editor.putInt(key, value as Int)
            is Boolean -> editor.putBoolean(key, value as Boolean)
        }
        return editor.commit()
    }


    fun <T> getPreference(context: Context, key: String, defaultValue: T): T {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        when (defaultValue) {
            is String -> return preferences.getString(key, defaultValue as String) as T
            is Float -> return preferences.getFloat(key, defaultValue as Float) as T
            is Int -> return preferences.getInt(key, defaultValue as Int) as T
            is Boolean -> return preferences.getBoolean(key, defaultValue as Boolean) as T
        }
        return defaultValue
    }
}