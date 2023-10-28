package com.example.flygame.gamefield.models

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

enum class DirectionMove {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    FORWARD,
    BACK,
    NULL
}

data class MoveData(
    val coordinates: Coordinates,
    val move: DirectionMove
)

data class FinalMoveData(
    val coordinates: Coordinates,
    val moves: List<DirectionMove>
)

val TAG = "MyTag"