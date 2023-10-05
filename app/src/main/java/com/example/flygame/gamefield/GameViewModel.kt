package com.example.flygame.gamefield

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.App
import com.example.flygame.settings.SettingsStore
import com.example.flygame.settings.models.Coordinates
import com.example.flygame.settings.models.SettingsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val settingsStore: SettingsStore
) : ViewModel(), TextToSpeech.OnInitListener {

    private val _stateCoordinatesFly: MutableStateFlow<Coordinates> = MutableStateFlow(Coordinates())
    val stateCoordinatesFly: StateFlow<Coordinates> = _stateCoordinatesFly

    private val _stateGameProcess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val stateGameProcess: StateFlow<Boolean> = _stateGameProcess

    private val _stateWaitingResponse: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val stateWaitingResponse: StateFlow<Boolean> = _stateWaitingResponse

    private val textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)
    private var coordinatesFly = Coordinates()
    private var job: Job? = null

    init {
        viewModelScope.launch {
            settingsStore.getData().collect {
                settingInitialCoordinates(it)
                job?.cancel()
            }
        }
    }

    private suspend fun settingInitialCoordinates(settingsData: SettingsData) {
        val center = settingsData.tableSize / 2
        coordinatesFly = Coordinates(
            horizontalX = center,
            verticalY = center,
            volumeZ = if (settingsData.isVolume) center else -1
        )
        _stateCoordinatesFly.emit(coordinatesFly)
    }

    fun startGame() {
        job = viewModelScope.launch {
            settingsStore.getData().collect {

                _stateGameProcess.emit(true)
                settingInitialCoordinates(it)
                gameProcess(it.tableSize, it.numberOfMoves, it.isVolume)
                _stateWaitingResponse.emit(true)
                job?.cancel()
            }
//            onResult(result) // onResult is called on the main thread
        }
    }


    private suspend fun gameProcess(tableSize: Int, numberOfMoves: Int, isVolume: Boolean) {
        for (i in 1..numberOfMoves) {
            coordinatesFly = GameMoves.getMove(coordinatesFly, tableSize, isVolume)
            _stateCoordinatesFly.emit(coordinatesFly)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
//                btnObjectDetection.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    fun stopGame() {
        viewModelScope.launch{
            _stateGameProcess.emit(false)
            _stateWaitingResponse.emit(false)
        }
    }
}