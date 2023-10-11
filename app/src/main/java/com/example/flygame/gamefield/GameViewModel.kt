package com.example.flygame.gamefield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.settings.SettingsStore
import com.example.flygame.settings.models.SettingsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val settingsStore: SettingsStore,
    private val gameMoves: GameMoves
) : ViewModel(), DefaultLifecycleObserver {

    private val _stateCoordinatesFly: MutableStateFlow<Coordinates> = MutableStateFlow(Coordinates())
    val stateCoordinatesFly: StateFlow<Coordinates> = _stateCoordinatesFly

    private val _stateGameStatus: MutableStateFlow<GameStatus> = MutableStateFlow(GameStatus.STOP)
    val stateGameStatus: StateFlow<GameStatus> = _stateGameStatus

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

    fun startGame() {
        job = viewModelScope.launch {
            settingsStore.getData().collect {
                _stateGameStatus.emit(GameStatus.GIVE_COMMANDS)
                settingInitialCoordinates(it)
                val isSuccessful = gameProcess(it.tableSize, it.numberOfMoves, it.isVolume)
                if (isSuccessful)
                    _stateGameStatus.emit(GameStatus.WAITING_RESPONSE)

                job?.cancel()
            }
        }
    }


    private suspend fun gameProcess(tableSize: Int, numberOfMoves: Int, isVolume: Boolean): Boolean {
        for (i in 1..numberOfMoves) {
            if (stateGameStatus.value == GameStatus.STOP) return false
            coordinatesFly = gameMoves.getMove(coordinatesFly, tableSize, isVolume)
            _stateCoordinatesFly.emit(coordinatesFly)
        }
        return true
    }

    fun stopGame() {
        viewModelScope.launch{
            _stateGameStatus.emit(GameStatus.STOP)
        }
    }

    private fun breakGame() {
        viewModelScope.launch {
            _stateGameStatus.emit(GameStatus.STOP)
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