package com.example.flygame.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.settings.repository.SettingsStore
import com.example.flygame.settings.models.SettingsData
import com.example.flygame.settings.models.SettingsViewData
import com.example.flygame.settings.models.listNumberOfMoves
import com.example.flygame.settings.models.listSpeed
import com.example.flygame.settings.models.listTableSizes
import com.example.flygame.settings.models.listTypesCommands
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
        spinnerTypesCommands = listTypesCommands.indexOf(data.typesCommands),
        spinnerNumberOfMoves = listNumberOfMoves.indexOf(data.numberOfMoves.toString()),
        spinnerIsHideField = data.isHideField
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