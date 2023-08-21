package com.example.flygame.instructions

import android.util.Log

class InstructionsModel {

    fun loadInstruction(): ArrayList<String> {

        val instructionsList = ArrayList<String>(2)
        instructionsList.add("123")
        instructionsList.add("456")
        Log.d("TagCheck", "Model")

        return instructionsList
    }

}