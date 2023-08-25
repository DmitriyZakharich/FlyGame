package com.example.flygame.gamefield

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.App
import com.example.flygame.settings.PreferencesReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel(), TextToSpeech.OnInitListener {

    private val _liveDataCoordinates: MutableLiveData<Triple<Int, Int, Int>> = MutableLiveData()
    val liveDataCoordinates: LiveData<Triple<Int, Int, Int>> = _liveDataCoordinates

    private val _liveDataGameProcess: MutableLiveData<Boolean> = MutableLiveData()
    val liveDataGameProcess: LiveData<Boolean> = _liveDataGameProcess

    private val textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)
    private val tableSize = PreferencesReader.tableSize         //Начало с 0 до n (не включая)
    private var coordinateVolumeZ = -1
    private var coordinateVerticalY = -1
    private var coordinateHorizontalX = -1


    init {
        settingInitialCoordinates()
    }

    private fun settingInitialCoordinates() {

        val coordinates=  if (tableSize % 2 != 0) tableSize / 2 else tableSize / 2 - 1


        coordinateVolumeZ = if (PreferencesReader.isVolumeTable) coordinates else 0
        coordinateVerticalY = coordinates
        coordinateHorizontalX = coordinates
    }

    fun startGame() {
        viewModelScope.launch {
            setLiveDataGameProcess(true)
            gameProcess()
            setLiveDataGameProcess(false)
//            onResult(result) // onResult is called on the main thread
        }
    }

    private fun setLiveDataGameProcess(clickable: Boolean) {
        _liveDataGameProcess.value = clickable
    }

    private suspend fun gameProcess() {
        viewModelScope.launch {

            Log.d(
                "tagTag123321",
                "start  z = $coordinateVolumeZ,  y = $coordinateVerticalY, x = $coordinateHorizontalX"
            )

//            var count = 0
//            var notification = ""
//            var previousMove = Pair("", 0)



            for (i in 0..5) {
                GameMoves.getMove(coordinateHorizontalX, coordinateVerticalY, coordinateVolumeZ, tableSize)
            }


//            do {
//                val moveDirection = GameMoves.getMovePlane()
//                val move = GameMoves.getPlusOrMinus()
//
//                var successfulMove = false
//
//                //Запрет хода назад
//                //Если x++, то нельзя x--
//                if (previousMove == Pair(moveDirection, -move)) {
//                    continue
//                }
//
//                when (moveDirection) {
//                    "X" -> if ((coordinateHorizontalX + move) >= 0 && (coordinateHorizontalX + move) <= tableSize) {
//                        coordinateHorizontalX += move
//                        notification = if (move < 0) "Влево" else "Вправо"
//                        successfulMove = true
//                    }
//
//
//                    "Y" -> if ((coordinateVerticalY + move) >= 0 && (coordinateVerticalY + move) <= tableSize) {
//                        coordinateVerticalY += move
//                        notification = if (move < 0) "Вверх" else "Вниз"
//                        successfulMove = true
//                    }
//
//
//                    "Z" -> if ((coordinateVolumeZ + move) >= 0 && (coordinateVolumeZ + move) <= tableSize) {
//                        coordinateVolumeZ += move
//                        notification = if (move < 0) "Вперед" else "Назад"
//                        successfulMove = true
//                    }
//                }


//                if (successfulMove) {
////                    Log.d("tagTag123321", "Ход: $notification")
////                    Log.d(
////                        "tagTag123321",
////                        "Позиция: $coordinateVolumeZ$coordinateVerticalY$coordinateHorizontalX"
////                    )
//                    textToSpeech.language = Locale("ru")
//                    textToSpeech.speak(notification, TextToSpeech.QUEUE_FLUSH, null, "")
//
//                    delay(1500L)
//
////                    count++
////                    previousMove = Pair(moveDirection, move)
//
//                }

//            } while (count < 5)

//            Log.d(
//                "tagTag123321",
//                "Конец: $coordinateVolumeZ $coordinateVerticalY $coordinateHorizontalX"
//            )

        }

    }

    fun cellClickListener(id: Int) {

        when (id) {
            in 100..999 ->
                if ("$id"[0].digitToInt() == coordinateVolumeZ
                    && "$id"[1].digitToInt() == coordinateVerticalY
                    && "$id"[2].digitToInt() == coordinateHorizontalX)
                    Log.d("tagTag123321", "---------Ура--------")

            in 10..99 ->
                if (coordinateVolumeZ == 0
                    && "$id"[1].digitToInt() == coordinateVerticalY
                    && "$id"[2].digitToInt() == coordinateHorizontalX)
                    Log.d("tagTag123321", "---------Ура--------")

            in 0..9 ->
                if (coordinateVolumeZ == 0
                    && coordinateVerticalY == 0
                    && "$id"[0].digitToInt() == coordinateHorizontalX)
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