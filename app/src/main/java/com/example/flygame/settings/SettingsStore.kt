package com.example.flygame.settings

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.flygame.settings.models.SettingsData
import com.example.flygame.settings.models.TAG
import com.example.flygame.settings.viewstate.SettingsState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsStore @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("game_settings")
    }

    fun getData(): Flow<SettingsData> = context.dataStore.data.map { preferences ->
        Log.d(TAG, "SettingsStore getData")

        SettingsData(
            tableSize = preferences[intPreferencesKey("table_size_key")] ?: 0,
            isVolume = preferences[booleanPreferencesKey("volume_key")] ?: false,
            speed = preferences[intPreferencesKey("speed_key")] ?: 0,
            voice = preferences[stringPreferencesKey("voice_key")] ?: "",
            numberOfMoves = preferences[intPreferencesKey("number_of_moves_key")] ?: 0,
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
            }
        }
    }
}