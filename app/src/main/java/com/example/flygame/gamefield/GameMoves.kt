package com.example.flygame.gamefield

import com.example.flygame.settings.PreferencesReader

object GameMoves {

    private val moveDirection = listOf("X", "Y", "Z")
    private val plusOrMinus = listOf(-1, 1)

    fun getMovePlane(): String {
        val n = if (!PreferencesReader.isVolumeTable) 1 else 2
        val random = (0..n).random()
        return moveDirection[random]
    }

    fun getPlusOrMinus(): Int {
        val random = (0..1).random()
        return plusOrMinus[random]
    }
}