package com.example.flygame.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.settings.models.SettingsData
import com.example.flygame.settings.models.SettingsViewData
import com.example.flygame.settings.models.TAG
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
class SettingsViewModel @Inject constructor(
    private val settingsStore: SettingsStore
) : ViewModel() {

    val data: StateFlow<SettingsViewData> = settingsStore
        .getData()
        .map{ mapperToView(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsViewData()
        )

    private fun mapperToView(data: SettingsData): SettingsViewData = SettingsViewData(
        spinnerTableSize = data.tableSize - 3,
        spinnerIsVolume = data.isVolume,
        spinnerSpeed = data.speed - 1,
        spinnerVoice = listVoiceArrows.indexOf(data.voice),
        spinnerNumberOfMoves = data.numberOfMoves - 3
    )

    fun spinnerItemSelected(state: SettingsState) {
        Log.d(TAG, "SettingsViewModel spinnerItemSelected")
        viewModelScope.launch {
            settingsStore.saveToken(state)
        }
    }

    fun clickListener(state: SettingsState) {
        Log.d(TAG, "SettingsViewModel clickListener")

        viewModelScope.launch {
            settingsStore.saveToken(state)
        }
    }
}