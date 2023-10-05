package com.example.flygame.gamefield

import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.flygame.App
import com.example.flygame.settings.models.Coordinates
import kotlinx.coroutines.delay
import java.util.Locale

object GameMoves: TextToSpeech.OnInitListener {

    private val moveDirection = listOf("X", "Y", "Z")
    private val plusOrMinus = listOf(-1, 1)
    private var previousMove = Pair("", 0)
    private val textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)

    init {
        announcement("")
    }


    suspend fun getMove(coordinatesFly: Coordinates, tableSize: Int, isVolume: Boolean): Coordinates {
        var notification = ""
        var successfulMove = false
        var newCoordinates = Coordinates()

        while (!successfulMove){

            val moveDirection = getMovePlane(isVolume)
            val move = getPlusOrMinus()

            if (previousMove == Pair(moveDirection, -move))
                continue

            previousMove = Pair(moveDirection, move)


            when (moveDirection) {
                "X" -> if ((coordinatesFly.horizontalX + move) in 0 until tableSize) {

                    newCoordinates = Coordinates(
                        horizontalX = coordinatesFly.horizontalX + move,
                        verticalY = coordinatesFly.verticalY,
                        volumeZ = coordinatesFly.volumeZ)

                    notification = if (move < 0) "Влево" else "Вправо"
                    successfulMove = true
                }

                "Y" -> if ((coordinatesFly.verticalY + move) in 0 until tableSize) {

                    newCoordinates = Coordinates(
                        horizontalX = coordinatesFly.horizontalX,
                        verticalY = coordinatesFly.verticalY + move,
                        volumeZ = coordinatesFly.volumeZ)

                    notification = if (move < 0) "Вверх" else "Вниз"
                    successfulMove = true
                }


                "Z" -> if ((coordinatesFly.volumeZ + move) in 0 until tableSize) {

                    newCoordinates = Coordinates(
                        horizontalX = coordinatesFly.horizontalX,
                        verticalY = coordinatesFly.verticalY,
                        volumeZ = coordinatesFly.volumeZ + move)

                    notification = if (move < 0) "Назад" else "Вперед"
                    successfulMove = true
                }
            }
        }

        announcement(notification)
        delay(1500L)
        return newCoordinates
    }

    private fun announcement(notification: String) {
        textToSpeech.language = Locale("ru")
        textToSpeech.speak(notification, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun getMovePlane(isVolume: Boolean): String {
        val n = if (isVolume) 2 else 1
        val random = (0 .. n).random()
        return moveDirection[random]
    }

    private fun getPlusOrMinus(): Int = plusOrMinus.random()

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