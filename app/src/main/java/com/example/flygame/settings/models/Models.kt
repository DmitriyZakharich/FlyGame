package com.example.flygame.settings.models

data class SettingsData(
    val tableSize: Int = 3,
    val isVolume: Boolean = false,
    val speed: Int = 5,
    val voice: String = "Voice",
    val numberOfMoves: Int = 5
)

data class SettingsViewData(
    val spinnerTableSize: Int = 0,      //table size = 3    -    [(3), 4, 5, 6, 7, 8, 9, 10]
    val spinnerIsVolume: Boolean = false,
    val spinnerSpeed: Int = 2,          //speed = 3    -    [1, 2, (3), 4, 5, 6, 7, 8, 9, 10]
    val spinnerVoice: Int = 0,          //voice = "voice"   ["Voice", "Arrows"]
    val spinnerNumberOfMoves: Int = 2   //number of moves = 5   -   [3, 4, (5), 6, 7, 8, 9, 10]
)

val listTableSizes = listOf("3", "4", "5", "6", "7", "8", "9", "10")
val listSpeed = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
val listVoiceArrows = listOf("Voice", "Arrows")
val listNumberOfMoves = listOf("3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")

val TAG = "3232321515123e33"