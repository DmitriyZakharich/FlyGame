package com.example.flygame.instructions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.settings.models.SettingsData
import com.example.flygame.settings.models.SettingsViewData
import com.example.flygame.settings.models.listNumberOfMoves
import com.example.flygame.settings.models.listSpeed
import com.example.flygame.settings.models.listTableSizes
import com.example.flygame.settings.models.listVoiceArrows
import com.example.flygame.settings.viewstate.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor(
    private val appSettingsStore: AppSettingsStore
) : ViewModel(){

    val data: StateFlow<AppSettingsData> = appSettingsStore
        .getData()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppSettingsData()
        )

    fun setAppSettings(state: AppSettingsState) {
        viewModelScope.launch {
            appSettingsStore.saveToken(state)
        }
    }
}