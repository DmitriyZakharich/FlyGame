package com.example.flygame.settings

import android.content.Context.MODE_PRIVATE
import com.example.flygame.App

class PreferencesReader {

    companion object {

        val isVolumeTable: Boolean
            get() {
                val settings = App.appContext.getSharedPreferences(
                    PreferencesWriter.APP_PREFERENCES,
                    MODE_PRIVATE
                )
                return settings.getBoolean(PreferencesWriter.KEY_SWITCH_VOLUME_TABLE, false)
            }

        val tableSize: Int
            get() {
                val settings = App.appContext.getSharedPreferences(
                    PreferencesWriter.APP_PREFERENCES,
                    MODE_PRIVATE
                )
                val adjustmentValue = 2 //3x3 - position №0 in Spinner
                                        //Начало таблицы с 0
                                        //Значения 0,1,2
                return settings.getInt(PreferencesWriter.KEY_TABLE_SIZE, 0) + adjustmentValue
            }
    }
}