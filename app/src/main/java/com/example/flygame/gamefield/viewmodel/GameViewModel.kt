package com.example.flygame.gamefield.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.gamefield.domain.Announcer
import com.example.flygame.gamefield.models.Coordinates
import com.example.flygame.gamefield.models.DirectionMove
import com.example.flygame.gamefield.models.GameStatus
import com.example.flygame.gamefield.domain.MoveManager
import com.example.flygame.settings.repository.SettingsStore
import com.example.flygame.settings.models.SettingsData
import com.example.flygame.settings.models.listTypesCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val settingsStore: SettingsStore,
    private val moveManager: MoveManager,
    private val announcer: Announcer
) : ViewModel(), DefaultLifecycleObserver, IGameViewModel {

    private val _stateCoordinatesFly: MutableStateFlow<Coordinates> = MutableStateFlow(Coordinates())
    override val stateCoordinatesFly: StateFlow<Coordinates> = _stateCoordinatesFly

    private val _stateGameStatus: MutableStateFlow<GameStatus> = MutableStateFlow(GameStatus.STOP)
    override val stateGameStatus: StateFlow<GameStatus> = _stateGameStatus

    private val _stateCommandArrow: MutableStateFlow<DirectionMove> = MutableStateFlow(DirectionMove.NULL)
    override val stateCommandArrow: StateFlow<DirectionMove> = _stateCommandArrow

    private var coordinatesFly = Coordinates()
    private var job: Job? = null

    init {
        viewModelScope.launch {
            settingsStore.getData().collect {
                settingInitialCoordinates(it)
                job?.cancel()
            }
        }
    }

    private suspend fun settingInitialCoordinates(settingsData: SettingsData) {
        val center = settingsData.tableSize / 2
        coordinatesFly = Coordinates(
            horizontalX = center,
            verticalY = center,
            volumeZ = if (settingsData.isVolume) center else -1
        )
        _stateCoordinatesFly.emit(coordinatesFly)
    }

    override fun startGame() {
        job = viewModelScope.launch {
            settingsStore.getData().collect {
                _stateGameStatus.emit(GameStatus.GIVE_COMMANDS)
                settingInitialCoordinates(it)

                val finalMoveData = moveManager.start(coordinatesFly, it.tableSize, it.numberOfMoves, it.isVolume)
                _stateCoordinatesFly.emit(finalMoveData.coordinates)

                when (it.typesCommands) {
                    listTypesCommands[0] -> {
                        _stateCommandArrow.emit(DirectionMove.NULL)
                        announcer.start(finalMoveData.moves)
                    }

                    listTypesCommands[1] -> {
                        finalMoveData.moves.forEach { directionMove ->
                            _stateCommandArrow.emit(directionMove)
                            delay(1000)
                            _stateCommandArrow.emit(DirectionMove.NULL)
                            delay(100)
                        }

                    }
                }
                _stateGameStatus.emit(GameStatus.WAITING_RESPONSE)
                job?.cancel()
            }
        }
    }

    override fun endGame() {
        viewModelScope.launch{
            _stateGameStatus.emit(GameStatus.STOP)
            _stateCommandArrow.emit(DirectionMove.NULL)
        }
    }

    private fun breakGame() {
        viewModelScope.launch {
            _stateGameStatus.emit(GameStatus.STOP)
            _stateCommandArrow.emit(DirectionMove.NULL)
            settingsStore.getData().collect {
                settingInitialCoordinates(it)
                job?.cancel()
            }
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        breakGame()
    }
}

@Composable
fun <viewModel : LifecycleObserver> viewModel.observeLifecycleEvents(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@observeLifecycleEvents)
        onDispose {
            lifecycle.removeObserver(this@observeLifecycleEvents)
        }
    }
}