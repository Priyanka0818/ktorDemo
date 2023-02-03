package com.app.ktorcrud.utils

import android.content.SharedPreferences

class SharedPreferenceUtils(private val sharedPreferences: SharedPreferences) {

    private val sharedPreferenceEditor = sharedPreferences.edit()

    fun preferencePutInteger(key: String?, value: Int) {
        sharedPreferenceEditor.putInt(key, value)
        sharedPreferenceEditor.commit()
    }

    fun preferenceGetInteger(key: String?, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun preferencePutBoolean(key: String?, value: Boolean) {
        sharedPreferenceEditor.putBoolean(key, value)
        sharedPreferenceEditor.commit()
    }

    fun preferenceGetBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun preferencePutString(key: String?, value: String?) {
        sharedPreferenceEditor.putString(key, value)
        sharedPreferenceEditor.commit()
    }

    fun removeKey(key: String?) {
        sharedPreferenceEditor.remove(key)
        sharedPreferenceEditor.commit()
    }

    fun preferenceGetString(key: String?): String {
        return sharedPreferences.getString(key, "")!!
    }

    fun preferenceGetString(key: String?, value: String?): String {
        return sharedPreferences.getString(key, value)!!
    }

    fun preferencePutLong(key: String?, value: Long) {
        sharedPreferenceEditor.putLong(key, value)
        sharedPreferenceEditor.commit()
    }

    fun preferenceGetLong(key: String?, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun preferencePutFloat(key: String?, value: Float) {
        sharedPreferenceEditor.putFloat(key, value)
        sharedPreferenceEditor.commit()
    }

    fun preferenceGetFloat(key: String?, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun hasPreferenceKey(key: String?): Boolean {
        return sharedPreferences.contains(key)
    }

    fun preferenceRemoveKey(key: String?) {
        sharedPreferenceEditor.remove(key)
        sharedPreferenceEditor.commit()
    }

    fun clearPreference() {
        sharedPreferenceEditor.clear()
        sharedPreferenceEditor.commit()
    }
}