package com.example.flygame.instructions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InstructionsViewModel: ViewModel() {

    private var instructionsLiveData: MutableLiveData<ArrayList<String>>? = null

    fun getInstructionsData(): MutableLiveData<ArrayList<String>>? {
        Log.d("TagCheck", "getInstructionsData")

        instructionsLiveData = MutableLiveData()

        instructionsLiveData?.value = InstructionsModel().loadInstruction()
        Log.d("TagCheck", "$instructionsLiveData")


        return instructionsLiveData
    }
}