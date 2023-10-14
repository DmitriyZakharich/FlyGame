package com.example.flygame.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.flygame.instructions.TypesCommands
import com.example.flygame.settings.models.SettingsData
import com.example.flygame.settings.models.SettingsKeys
import com.example.flygame.settings.models.listTypesCommands
import com.example.flygame.settings.viewstate.SettingsState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsStore @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("game_settings")
    }

    fun getData(): Flow<SettingsData> = context.dataStore.data.map { preferences ->
        SettingsData(
            tableSize = preferences[intPreferencesKey(SettingsKeys.TABLE_SIZE_KEY)] ?: 3,
            isVolume = preferences[booleanPreferencesKey(SettingsKeys.VOLUME_KEY)] ?: false,
            speed = preferences[intPreferencesKey(SettingsKeys.SPEED_KEY)] ?: 5,
            typesCommands = preferences[stringPreferencesKey(SettingsKeys.TYPE_COMMANDS_KEY)] ?: listTypesCommands[0],
            numberOfMoves = preferences[intPreferencesKey(SettingsKeys.NUMBER_OF_MOVES_KEY)] ?: 5,
            isHideField = preferences[booleanPreferencesKey(SettingsKeys.HIDE_FIELD_KEY)] ?: false,
        )
    }

    suspend fun saveToken(state: SettingsState) {
        context.dataStore.edit { preferences ->
            when (state) {
                is SettingsState.TableSize -> { preferences[state.key] = state.data }
                is SettingsState.Volume -> { preferences[state.key] = state.data }
                is SettingsState.Speed -> { preferences[state.key] = state.data }
                is SettingsState.Voice -> { preferences[state.key] = state.data }
                is SettingsState.NumberOfMoves -> { preferences[state.key] = state.data }
                is SettingsState.HideField -> { preferences[state.key] = state.data }
            }
        }
    }
}