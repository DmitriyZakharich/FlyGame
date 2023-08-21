package com.example.flygame.settings

import android.content.Context
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
import com.example.flygame.settings.viewstate.SettingsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("game_settings")
    }

//    val getAccessToken: Flow<Int> = context.dataStore.data.map { preferences ->
//        preferences[TABLE_SIZE_KEY] ?: 5
//    }

    /**Не нужно вытягивать данные по одному
     * getData Нужна для SettingsScreen только как вытащить все данные(создать Модель)
     * В игре так же лучше вытягивать модеть целиков
    */
    suspend fun getData(): Flow<SettingsData> = context.dataStore.data.map { preferences ->
        SettingsData(
            tableSize = preferences[intPreferencesKey("table_size_key")] ?: 0,
            volume = preferences[booleanPreferencesKey("volume_key")] ?: false,
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

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Main() {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val tokenValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val store = SettingsStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    Column(
        modifier = Modifier.clickable { keyboardController?.hide() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(text = tokenText.value)

        TextField(
            value = tokenValue.value,
            onValueChange = { tokenValue.value = it },
        )

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    store.saveToken(tokenValue.value.text)
                }
            }
        ) {
            Text(text = "Update Token")
        }
    }
}