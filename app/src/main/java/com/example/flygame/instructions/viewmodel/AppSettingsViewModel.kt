package com.example.flygame.instructions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.instructions.repository.AppSettingsData
import com.example.flygame.instructions.repository.AppSettingsState
import com.example.flygame.instructions.repository.AppSettingsStore
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