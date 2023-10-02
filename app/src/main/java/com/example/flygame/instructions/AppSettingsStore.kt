package com.example.flygame.instructions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppSettingsStore @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore("app_settings")
    }

    fun getData(): Flow<AppSettingsData> = context.appDataStore.data.map { preferences ->
        AppSettingsData(
            showInstructions = preferences[booleanPreferencesKey("instructions_key")] ?: true
        )
    }

    suspend fun saveToken(state: AppSettingsState) {
        context.appDataStore.edit { preferences ->
            when (state) {
                is AppSettingsState.Instructions -> { preferences[state.key] = state.data }
            }
        }
    }
}

sealed class AppSettingsState {
    data class Instructions(val data: Boolean) : AppSettingsState() {
        val key: Preferences.Key<Boolean> = booleanPreferencesKey("instructions_key")
    }
}

data class AppSettingsData(
    val showInstructions: Boolean = false
)