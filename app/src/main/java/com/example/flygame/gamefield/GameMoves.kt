package com.example.flygame.gamefield

import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.flygame.App
import com.example.flygame.settings.PreferencesReader
import kotlinx.coroutines.delay
import java.util.Locale

object GameMoves: TextToSpeech.OnInitListener {

    private val moveDirection = listOf("X", "Y", "Z")
    private val plusOrMinus = listOf(-1, 1)
    private var notification = ""
    private var previousMove = Pair("", 0)
    private val textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)



    suspend fun getMove(coordinateHorizontalX: Int, coordinateVerticalY: Int, coordinateVolumeZ: Int, tableSize: Int): Triple<Int, Int,Int> {
        var newX = coordinateHorizontalX
        var newY = coordinateVerticalY
        var newZ = coordinateVolumeZ

        var successfulMove = false
        while (!successfulMove){

            val moveDirection = getMovePlane()
            val move = getPlusOrMinus()

            if (previousMove == Pair(moveDirection, -move))
                continue

            previousMove = Pair(moveDirection, move)

            when (moveDirection) {
                "X" -> if ((coordinateHorizontalX + move) in 0 until tableSize) {
                    newX += move
                    notification = if (move < 0) "Влево" else "Вправо"
                    successfulMove = true
                }

                "Y" -> if ((coordinateVerticalY + move) in 0 until tableSize) {
                    newY += move
                    notification = if (move < 0) "Вверх" else "Вниз"
                    successfulMove = true
                }


                "Z" -> if ((coordinateVolumeZ + move) in 0 until tableSize) {
                    newZ += move
                    notification = if (move < 0) "Вперед" else "Назад"
                    successfulMove = true
                }
            }
            successfulMove = true
            delay(1500L)

            textToSpeech.language = Locale("ru")
            textToSpeech.speak(notification, TextToSpeech.QUEUE_FLUSH, null, "")
        }
        return Triple(newX, newY, newZ)
    }

    private fun getMovePlane(): String {
        val n = if (!PreferencesReader.isVolumeTable) 1 else 2
        val random = (0 until n).random()
        return moveDirection[random]
    }

    private fun getPlusOrMinus(): Int {
        val random = (0 ..1).random()
        return plusOrMinus[random]
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