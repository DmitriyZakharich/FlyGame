package com.example.flygame.gamefield

import com.example.flygame.gamefield.domain.MoveManager
import com.example.flygame.gamefield.models.Coordinates
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class MoveManagerTest {

    private lateinit var moveManager: MoveManager

    @Before
    fun dd() {
        moveManager = MoveManager()

    }

    @Test
    fun the_array_size_equal_to_the_number_of_moves_should_be_returned() = runBlocking {
//        moveManager = MoveManager()
        val numberOfMoves = 5
        val moveData = moveManager.start(Coordinates(1,1,1), 3, numberOfMoves, false)
        assert(moveData.moves.size == numberOfMoves)
    }

    @Test
    fun the_results_obtained_are_not_equal_to_the_default_ones() = runBlocking {
//        moveManager = MoveManager()
        val moveData = moveManager.start(Coordinates(1,1,1), 3, 5, false)
        val defaultCoordinates = Coordinates()
        assert(moveData.coordinates != defaultCoordinates)
    }


}
