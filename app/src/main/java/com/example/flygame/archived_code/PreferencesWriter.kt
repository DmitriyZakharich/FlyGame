package com.example.flygame.archived_code

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import com.example.flygame.App

class PreferencesWriter {

    private val setting = App.appContext.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun putBoolean(key: String, isChecked: Boolean) {
        val ed = setting.edit()
        ed.putBoolean(key, isChecked)
        ed.apply()

    }

    fun putInt(key: String, value: Int) {
        val ed = setting.edit()
        ed.putInt(key, value)
        ed.apply()
    }

    companion object {
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val KEY_TABLE_SIZE = "KEY_TABLE_SIZE"
        const val KEY_SWITCH_VOLUME_TABLE = "KEY_SWITCH_VOLUME_TABLE"
    }


}