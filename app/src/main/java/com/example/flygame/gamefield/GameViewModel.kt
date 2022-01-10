package com.example.flygame.gamefield

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.App
import com.example.flygame.settings.PreferencesReader
import kotlinx.coroutines.*
import java.util.*

class GameViewModel : ViewModel(), TextToSpeech.OnInitListener {

    private var liveDataCoordinates: MutableLiveData<Triple<Int, Int, Int>> = MutableLiveData()
    private lateinit var startCoordinatesTriple: Triple<Int, Int, Int>
    private var textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)
    private var liveDataGameProcess: MutableLiveData<Boolean> = MutableLiveData()
    private var tableSize: Int = 0
    private lateinit var dataForCheck: Map<Int, String>

    init {
        settingInitialCoordinates()
    }

    private fun settingInitialCoordinates() {

        tableSize = PreferencesReader.tableSize         //Начало с 0
        val coordinates =
            tableSize / 2                 //Центр таблицы для нечетных, приблизительный центр для четных
        //Для таблицы из 3х стоблцов значение равно 1 (начало с 0)

        startCoordinatesTriple = if (PreferencesReader.isVolumeTable)
            Triple(coordinates, coordinates, coordinates)
        else
            Triple(0, coordinates, coordinates)

        liveDataCoordinates.value = startCoordinatesTriple
    }

    fun getLiveDataCoordinates() = liveDataCoordinates

    fun getLiveDataGameProcess() = liveDataGameProcess

    fun startGame() {

        viewModelScope.launch {
            setLiveDataGameProcess(true)
            gameProcess()
            setLiveDataGameProcess(false)
//            onResult(result) // onResult is called on the main thread
        }
    }

    private fun setLiveDataGameProcess(clickable: Boolean) {
        liveDataGameProcess.value = clickable
    }

    private suspend fun gameProcess() = coroutineScope {
        launch {

            var coordinateVolumeZ = startCoordinatesTriple.first
            var coordinateVerticalY = startCoordinatesTriple.second
            var coordinateHorizontalX = startCoordinatesTriple.third

            Log.d(
                "tagTag123321",
                "start  x = $coordinateHorizontalX,  y = $coordinateVerticalY, z = $coordinateVolumeZ"
            )

            var count = 0
            var notification = ""
            var previousMove = Pair("", 0)


            do {
                val moveDirection = GameMoves.getMovePlane()
                val move = GameMoves.getPlusOrMinus()
                Log.d("tagTag123321", "-------------------movePlane: $moveDirection    sign: $move")

                var successfulMove = false

                //Запрет хода назад
                //Если x++, то нельзя x--
                if (previousMove == Pair(moveDirection, -move)) {
                    previousMove = Pair(moveDirection, move)
                    Log.d("tagTag123321", "Ход назад")
                    continue
                }
                previousMove = Pair(moveDirection, move)

                when (moveDirection) {
                    "X" -> if ((coordinateHorizontalX + move) >= 0 && (coordinateHorizontalX + move) <= tableSize) {
                        coordinateHorizontalX += move
                        notification = if (move < 0) "Влево" else "Вправо"
                        successfulMove = true
                    }


                    "Y" -> if ((coordinateVerticalY + move) >= 0 && (coordinateVerticalY + move) <= tableSize) {
                        coordinateVerticalY += move
                        notification = if (move < 0) "Вверх" else "Вниз"
                        successfulMove = true
                    }


                    "Z" -> if ((coordinateVolumeZ + move) >= 0 && (coordinateVolumeZ + move) <= tableSize) {
                        coordinateVolumeZ += move
                        notification = if (move < 0) "Вперед" else "Назад"
                        successfulMove = true
                    }
                }


                Log.d("tagTag123321", "successfulMove: $successfulMove")
                Log.d(
                    "tagTag123321",
                    "x = $coordinateVerticalY,  y = $coordinateHorizontalX, z = $coordinateVolumeZ"
                )


                if (successfulMove) {
                    textToSpeech.language = Locale("ru")
                    textToSpeech.speak(notification, TextToSpeech.QUEUE_FLUSH, null, "")

                    delay(1500L)

                    count++
                    previousMove = Pair(moveDirection, move)

                }

            } while (count < 5)

            startCoordinatesTriple =
                Triple(coordinateVolumeZ, coordinateVerticalY, coordinateHorizontalX)

            Log.d("tagTag123321", "startCoordinatesPair: $startCoordinatesTriple")

        }
    }

    fun cellClickListener(id: Int) {

        when (id) {
            in 100..999 -> if ("$id"[0].digitToInt() == startCoordinatesTriple.third
                && "$id"[1].digitToInt() == startCoordinatesTriple.second
                && "$id"[2].digitToInt() == startCoordinatesTriple.first)
                Log.d("tagTag123321", "---------Ура--------")

            in 10..99 ->
                if ("$id"[0].digitToInt() == startCoordinatesTriple.second
                    && "$id"[1].digitToInt() == startCoordinatesTriple.third
                    && startCoordinatesTriple.first == 0)
                    Log.d("tagTag123321", "---------Ура--------")

            in 0..9 ->
                if ("$id"[0].digitToInt() == startCoordinatesTriple.third
                    && startCoordinatesTriple.second == 0
                    && startCoordinatesTriple.first == 0)
                    Log.d("tagTag123321", "---------Ура--------")
        }

//        if ("$id"[0].digitToInt() == startCoordinatesTriple.first
//            && "$id"[1].digitToInt() == startCoordinatesTriple.second) {
//            Log.d("tagTag123321", "---------Ура--------")
//        }
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

    fun setData(dataForCheck: MutableMap<Int, String>) {
        this.dataForCheck = dataForCheck
    }


}