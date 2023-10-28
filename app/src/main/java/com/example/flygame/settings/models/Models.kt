package com.example.flygame.settings.models

data class SettingsData(
    val tableSize: Int = listTableSizes[0].toInt(),
    val isVolume: Boolean = false,
    val speed: Int = 5,
    val typesCommands: String = listTypesCommands[0],
    val numberOfMoves: Int = 5,
    val isHideField: Boolean = false
)

data class SettingsViewData(
    val spinnerTableSize: Int = 0,      //table size = 3    -    [(3), 5, 7, 9]
    val spinnerIsVolume: Boolean = false,
    val spinnerSpeed: Int = 2,          //speed = 3    -    [1, 2, (3), 4, 5, 6, 7, 8, 9, 10]
    val spinnerTypesCommands: Int = 0,          //voice = "voice"   ["Voice", "Arrows"]
    val spinnerNumberOfMoves: Int = 1,   //number of moves = 5   -   [3, (5), 7, 9, 10, 15, 20]
    val spinnerIsHideField: Boolean = false
)

val listTableSizes = listOf("3", "5", "7"/*, "9"*/)
val listSpeed = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
val listTypesCommands = listOf("Voice", "Arrows")
val listNumberOfMoves = listOf("3", "5", "7", "10", "15", "20")

object SettingsKeys {
    const val TABLE_SIZE_KEY = "table_size_key"
    const val VOLUME_KEY = "volume_key"
    const val SPEED_KEY = "speed_key"
    const val TYPE_COMMANDS_KEY = "type_commands_key"
    const val NUMBER_OF_MOVES_KEY = "number_of_moves_key"
    const val HIDE_FIELD_KEY = "hide_field_key"
}