package com.example.flygame.gamefield

import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.flygame.App
import kotlinx.coroutines.delay
import java.util.Locale
import javax.inject.Inject

class GameMoves @Inject constructor(): TextToSpeech.OnInitListener {

    private val moveDirection = listOf("X", "Y", "Z")
    private val plusOrMinus = listOf(-1, 1)
    private var previousMove = Pair("", 0)
    private val textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)

    suspend fun getMove(
        coordinatesFly: Coordinates,
        tableSize: Int,
        isVolume: Boolean
    ): Coordinates {

        var notification = ""
        var successfulMove = false
        var newCoordinates = Coordinates()

        while (!successfulMove) {

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
                        volumeZ = coordinatesFly.volumeZ
                    )

                    notification = if (move < 0) "Влево" else "Вправо"
                    successfulMove = true
                }

                "Y" -> if ((coordinatesFly.verticalY + move) in 0 until tableSize) {

                    newCoordinates = Coordinates(
                        horizontalX = coordinatesFly.horizontalX,
                        verticalY = coordinatesFly.verticalY + move,
                        volumeZ = coordinatesFly.volumeZ
                    )

                    notification = if (move < 0) "Вверх" else "Вниз"
                    successfulMove = true
                }


                "Z" -> if ((coordinatesFly.volumeZ + move) in 0 until tableSize) {

                    newCoordinates = Coordinates(
                        horizontalX = coordinatesFly.horizontalX,
                        verticalY = coordinatesFly.verticalY,
                        volumeZ = coordinatesFly.volumeZ + move
                    )

                    notification = if (move < 0) "Назад" else "Вперед"
                    successfulMove = true
                }
            }
        }

        announcement(notification)
        return newCoordinates
    }

    private suspend fun announcement(notification: String) {
        textToSpeech.setSpeechRate(3f)
        textToSpeech.language = Locale("ru")


//        val n = arrayListOf(
//            "старт",
//            "вверх",
//            "вниз",
//            "вверх",
//            "спираль",
//            "вверх",
//            "вверх",
//            "стоп"
//        )
////        textToSpeech.speak(n.toString(), TextToSpeech.QUEUE_ADD, null, "")
//
//        n.forEach {
//            if (false)
//                delay(2000L)
//            textToSpeech.speak(it, TextToSpeech.QUEUE_ADD, null, "")
//        }

        textToSpeech.speak(notification, TextToSpeech.QUEUE_ADD, null, "")
        delay(1000L)
    }

    private fun getMovePlane(isVolume: Boolean): String {
        val n = if (isVolume) 2 else 1
        val random = (0..n).random()
        return moveDirection[random]
    }

    private fun getPlusOrMinus(): Int = plusOrMinus.random()

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTSаааааааааа", "The Language specified is not supported!")
            } else {
//                btnObjectDetection.isEnabled = true
            }
        } else {
            Log.e("TTSаааааааааа", "Initialization Failed!")
        }
    }
}