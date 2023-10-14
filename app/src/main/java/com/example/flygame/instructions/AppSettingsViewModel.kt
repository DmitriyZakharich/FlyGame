package com.example.flygame.instructions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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