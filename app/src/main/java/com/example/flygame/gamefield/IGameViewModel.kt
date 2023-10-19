package com.example.flygame.gamefield

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.StateFlow

interface IGameViewModel {
    val stateCoordinatesFly: StateFlow<Coordinates>
    val stateGameStatus: StateFlow<GameStatus>
    val stateCommandArrow: StateFlow<DirectionMove>
    fun startGame()
    fun endGame()
    fun onStop(owner: LifecycleOwner)
}