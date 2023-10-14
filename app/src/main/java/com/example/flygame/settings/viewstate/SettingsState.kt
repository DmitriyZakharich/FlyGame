package com.example.flygame.settings.viewstate

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.flygame.settings.models.SettingsKeys

sealed class SettingsState {
    data class TableSize(val data: Int) : SettingsState() {
        val key: Preferences.Key<Int> = intPreferencesKey(SettingsKeys.TABLE_SIZE_KEY)
    }

    data class Volume(val data: Boolean) : SettingsState() {
        val key: Preferences.Key<Boolean> = booleanPreferencesKey(SettingsKeys.VOLUME_KEY)
    }

    data class Speed(val data: Int) : SettingsState() {
        val key: Preferences.Key<Int> = intPreferencesKey(SettingsKeys.SPEED_KEY)
    }

    data class Voice(val data: String) : SettingsState() {
        val key: Preferences.Key<String> = stringPreferencesKey(SettingsKeys.TYPE_COMMANDS_KEY)
    }

    data class NumberOfMoves(val data: Int) : SettingsState() {
        val key: Preferences.Key<Int> = intPreferencesKey(SettingsKeys.NUMBER_OF_MOVES_KEY)
    }

    data class HideField(val data: Boolean) : SettingsState() {
        val key: Preferences.Key<Boolean> = booleanPreferencesKey(SettingsKeys.HIDE_FIELD_KEY)
    }
}
