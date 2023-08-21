package com.example.flygame.settings.viewstate

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

sealed class SettingsState {
    data class TableSize(val data: Int) : SettingsState() {
        val key: Preferences.Key<Int> = intPreferencesKey("table_size_key")
    }

    data class Volume(val data: Boolean) : SettingsState() {
        val key: Preferences.Key<Boolean> = booleanPreferencesKey("volume_key")
    }

    data class Speed(val data: Int) : SettingsState() {
        val key: Preferences.Key<Int> = intPreferencesKey("speed_key")
    }

    data class Voice(val data: String) : SettingsState() {
        val key: Preferences.Key<String> = stringPreferencesKey("voice_key")
    }

    data class NumberOfMoves(val data: Int) : SettingsState() {
        val key: Preferences.Key<Int> = intPreferencesKey("number_of_moves_key")
    }
}
