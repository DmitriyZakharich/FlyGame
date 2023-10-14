package com.example.flygame.gamefield

import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.flygame.App
import kotlinx.coroutines.delay
import java.util.LinkedList
import java.util.Locale
import javax.inject.Inject

class MoveManager @Inject constructor(): TextToSpeech.OnInitListener {

    private val moveDirection = listOf("X", "Y", "Z")
    private val plusOrMinus = listOf(-1, 1)
    private var previousMove = Pair("", 0)
    private val textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)

    suspend fun start(coordinatesFly: Coordinates, tableSize: Int, numberOfMoves: Int, isVolume: Boolean): FinalMoveData {
        val moves = LinkedList<DirectionMove>()
        var newCoordinates = coordinatesFly

        for (i in 1..numberOfMoves) {
            val moveData = getMove(newCoordinates, tableSize, isVolume)
            moves.add(moveData.move)
            newCoordinates = moveData.coordinates
        }
        return FinalMoveData(newCoordinates, moves)
    }

    private suspend fun getMove(
        coordinatesFly: Coordinates,
        tableSize: Int,
        isVolume: Boolean
    ): MoveData {

        var directionMove = DirectionMove.UP
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

                    directionMove = if (move < 0) DirectionMove.LEFT else DirectionMove.RIGHT
                    successfulMove = true
                }

                "Y" -> if ((coordinatesFly.verticalY + move) in 0 until tableSize) {

                    newCoordinates = Coordinates(
                        horizontalX = coordinatesFly.horizontalX,
                        verticalY = coordinatesFly.verticalY + move,
                        volumeZ = coordinatesFly.volumeZ
                    )

                    directionMove = if (move < 0) DirectionMove.UP else DirectionMove.DOWN
                    successfulMove = true
                }


                "Z" -> if ((coordinatesFly.volumeZ + move) in 0 until tableSize) {

                    newCoordinates = Coordinates(
                        horizontalX = coordinatesFly.horizontalX,
                        verticalY = coordinatesFly.verticalY,
                        volumeZ = coordinatesFly.volumeZ + move
                    )

                    directionMove = if (move < 0) DirectionMove.BACK else DirectionMove.FORWARD
                    successfulMove = true
                }
            }
        }
        return MoveData(newCoordinates, directionMove)
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