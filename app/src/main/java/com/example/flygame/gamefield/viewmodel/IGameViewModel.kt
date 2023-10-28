package com.example.flygame.gamefield.viewmodel

import androidx.lifecycle.LifecycleOwner
import com.example.flygame.gamefield.models.Coordinates
import com.example.flygame.gamefield.models.DirectionMove
import com.example.flygame.gamefield.models.GameStatus
import kotlinx.coroutines.flow.StateFlow

interface IGameViewModel {
    val stateCoordinatesFly: StateFlow<Coordinates>
    val stateGameStatus: StateFlow<GameStatus>
    val stateCommandArrow: StateFlow<DirectionMove>
    fun startGame()
    fun endGame()
    fun onStop(owner: LifecycleOwner)
}