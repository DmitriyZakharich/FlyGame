package com.example.flygame.gamefield

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.App
import com.example.flygame.settings.SettingsStore
import com.example.flygame.settings.models.CoordinatesFly
import com.example.flygame.settings.models.SettingsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val settingsStore: SettingsStore
) : ViewModel(), TextToSpeech.OnInitListener {

    private val _liveDataCoordinatesFly: MutableLiveData<CoordinatesFly> = MutableLiveData()
    val liveDataCoordinates: LiveData<CoordinatesFly> = _liveDataCoordinatesFly

    private val _liveDataGameProcess: MutableLiveData<Boolean> = MutableLiveData()
    val liveDataGameProcess: LiveData<Boolean> = _liveDataGameProcess

    private val textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)
//    private val tableSize = PreferencesReader.tableSize         //Начало с 0 до n (не включая)
    private val coordinatesFly = CoordinatesFly()
    private var job: Job? = null

//    init {
//        settingInitialCoordinates(it)
//    }

    private fun settingInitialCoordinates(settingsData: SettingsData) {

        val center=  settingsData.tableSize / 2

        coordinatesFly.volumeZ = if (settingsData.isVolume) center else 0
        coordinatesFly.verticalY = center
        coordinatesFly.horizontalX = center
        _liveDataCoordinatesFly.value = coordinatesFly

        Log.d("tagTag123321","settingInitialCoordinates")
        Log.d("tagTag123321","start  z = ${coordinatesFly.volumeZ},  y = ${coordinatesFly.verticalY}, x = ${coordinatesFly.horizontalX}")
    }

    fun startGame() {
        job = viewModelScope.launch {
            Log.d("tagTag123321","launch")

            settingsStore.getData().collect {
                Log.d("tagTag123321","collect")

                setLiveDataGameProcess(true)
                settingInitialCoordinates(it)
                gameProcess(it.tableSize, it.numberOfMoves, it.isVolume)
                setLiveDataGameProcess(false)
                job?.cancel()
            }
//            onResult(result) // onResult is called on the main thread
        }

    }

    private fun setLiveDataGameProcess(inProcess: Boolean) {
        _liveDataGameProcess.postValue(inProcess)
    }

    private suspend fun gameProcess(tableSize: Int, numberOfMoves: Int, isVolume: Boolean) {
//        viewModelScope.launch {

//            var count = 0
//            var notification = ""
//            var previousMove = Pair("", 0)


            for (i in 1..numberOfMoves) {
                Log.d("111111111tag","for $i")

                _liveDataCoordinatesFly.postValue(GameMoves.getMove(coordinatesFly, tableSize, isVolume))
            }
//        }
    }

    fun cellClickListener(id: Int) {
        Log.d("tagTag123321", "id = $id")

        when (id) {
            in 100..999 ->
                if ("$id"[0].digitToInt() == coordinatesFly.volumeZ
                    && "$id"[1].digitToInt() == coordinatesFly.verticalY
                    && "$id"[2].digitToInt() == coordinatesFly.horizontalX)
                    Log.d("tagTag123321", "---------Ура--------")

            in 10..99 ->
                if (coordinatesFly.volumeZ == 0
                    && "$id"[0].digitToInt() == coordinatesFly.verticalY
                    && "$id"[1].digitToInt() == coordinatesFly.horizontalX)
                    Log.d("tagTag123321", "---------Ура--------")

            in 0..9 ->
                if (coordinatesFly.volumeZ == 0
                    && coordinatesFly.verticalY == 0
                    && "$id"[0].digitToInt() == coordinatesFly.horizontalX)
                    Log.d("tagTag123321", "---------Ура--------")
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
}