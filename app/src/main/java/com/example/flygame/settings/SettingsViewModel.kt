package com.example.flygame.settings

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
        spinnerTableSize = listTableSizes.indexOf(data.tableSize.toString()),
        spinnerIsVolume = data.isVolume,
        spinnerSpeed = listSpeed.indexOf(data.speed.toString()),
        spinnerVoice = listVoiceArrows.indexOf(data.voice),
        spinnerNumberOfMoves = listNumberOfMoves.indexOf(data.numberOfMoves.toString())
    )

    fun spinnerItemSelected(state: SettingsState) {
        viewModelScope.launch {
            settingsStore.saveToken(state)
        }
    }

    fun clickListener(state: SettingsState) {
        viewModelScope.launch {
            settingsStore.saveToken(state)
        }
    }
}