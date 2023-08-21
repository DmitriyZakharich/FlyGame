package com.example.flygame.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flygame.App
import com.example.flygame.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private var preferencesWriter: PreferencesWriter = PreferencesWriter()
    var liveDataTableSize: MutableLiveData<Int> = MutableLiveData()
    var liveDataIsVolumeTable: MutableLiveData<Boolean> = MutableLiveData()

    //Spinners
    //TABLE_SIZE_KEY
    //SPEED_KEY
    //VOICE_KEY
    //NUMBER_OF_MOVES_KEY

    //VOLUME_KEY

    init {
        start()
    }

    private fun start() {
        liveDataIsVolumeTable.value = PreferencesReader.isVolumeTable
        liveDataTableSize.value = PreferencesReader.tableSize - 3 //3x3 - position №0 in Spinner
    }


    fun spinnerItemSelected(id: Int, position: Int) {
        when (id) {
            R.id.spinnerTableSize -> preferencesWriter.putInt(
                PreferencesWriter.KEY_TABLE_SIZE,
                position
            )
        }
    }

    fun clickListener(id: Int, isChecked: Boolean) {
        when (id) {
            R.id.switchVolumeTable -> preferencesWriter.putBoolean(
                PreferencesWriter.KEY_SWITCH_VOLUME_TABLE,
                isChecked
            )
        }

    }
}