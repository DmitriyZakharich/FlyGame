package com.example.flygame.gamefield

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flygame.settings.PreferencesReader

class GameViewModel : ViewModel() {

    private var liveDataCoordinates: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    private lateinit var startCoordinatesPair: Pair<Int, Int>

    init {
        start1()
    }

    private fun start1() {

        val tableSize = PreferencesReader.tableSize
        val coordinates = if (tableSize % 2 == 0) {
            tableSize / 2

        } else {
            tableSize / 2 + 1 //if tableSize 3x3 then start coordinates 2x2
        }

        startCoordinatesPair = Pair(coordinates, coordinates)
        liveDataCoordinates.value = startCoordinatesPair
    }


    fun getLiveDataCoordinates() = liveDataCoordinates

}