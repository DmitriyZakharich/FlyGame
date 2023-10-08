package com.example.flygame.gamefield

data class Coordinates(
    val horizontalX: Int = -1,
    val verticalY: Int = -1,
    val volumeZ: Int = -1
)

enum class GameStatus {
    STOP,
    GIVE_COMMANDS,
    WAITING_RESPONSE
}