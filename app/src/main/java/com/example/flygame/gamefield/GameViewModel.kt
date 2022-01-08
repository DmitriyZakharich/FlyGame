package com.example.flygame.gamefield

import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.App
import com.example.flygame.settings.PreferencesReader
import kotlinx.coroutines.*
import java.util.*

class GameViewModel : ViewModel(), TextToSpeech.OnInitListener {

    private var liveDataCoordinates: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    private lateinit var startCoordinatesPair: Pair<Int, Int>
    private var textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)
    private var liveDataGameProcess: MutableLiveData<Boolean> = MutableLiveData()

    init {
        settingInitialCoordinates()
    }

    private fun settingInitialCoordinates() {

        val tableSize = PreferencesReader.tableSize
        val coordinates = if (tableSize % 2 == 0) {
            tableSize / 2
        } else {
            tableSize / 2 + 1 //if tableSize 3x3 then start coordinates 2x2
        }

        startCoordinatesPair = Pair(coordinates, coordinates)
        liveDataCoordinates.value = startCoordinatesPair
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

            var moveHorizontally = startCoordinatesPair.first
            var moveVertically = startCoordinatesPair.second


            var count = 0
            var notification = ""

            do {
                val randomHorizontally = (-1..1).random()
                val randomVertically = (-1..1).random()

                if ((moveHorizontally + randomHorizontally) >= 0
                    && (moveHorizontally + randomHorizontally) < PreferencesReader.tableSize

                    && (moveVertically + randomVertically) >= 0
                    && (moveVertically + randomVertically) < PreferencesReader.tableSize

                    && !(moveHorizontally == 0 && moveVertically == 0)
                ) {


                if (randomHorizontally == 0 && randomVertically == -1)
                    notification ="Вверх"

                if (randomHorizontally == 0 && randomVertically == 1)
                    notification ="Вниз"

                if (randomHorizontally == -1 && randomVertically == 0)
                    notification ="Влево"

                if (randomHorizontally == 1 && randomVertically == 0)
                    notification ="Вправо"



                if (randomHorizontally == -1 && randomVertically == -1)
                    notification ="В - Л"

                if (randomHorizontally == -1 && randomVertically == 1)
                    notification ="В - П"

                if (randomHorizontally == 1 && randomVertically == -1)
                    notification ="Н - Л"

                if (randomHorizontally == 1 && randomVertically == 1)
                    notification ="Н - П"


                    moveHorizontally += randomHorizontally
                    moveVertically += randomVertically

                    Toast.makeText(
                        App.appContext,
                        "корутина : $moveHorizontally   :   $moveVertically",
                        Toast.LENGTH_SHORT
                    ).show()

                    textToSpeech.language = Locale("ru")
                    textToSpeech.speak(notification, TextToSpeech.QUEUE_FLUSH, null, "")

                    delay(1500L)


                    count++
                }

            } while (count < 5)

            startCoordinatesPair = Pair(moveHorizontally, moveVertically)

            Log.d("tagTag123321", "startCoordinatesPair: $startCoordinatesPair")
            Toast.makeText(
                App.appContext,
                "startCoordinatesPair: $startCoordinatesPair",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
//                btnObjectDetection.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }
}