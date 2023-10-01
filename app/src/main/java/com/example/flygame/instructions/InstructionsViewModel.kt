package com.example.flygame.instructions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructionsViewModel @Inject constructor(
    instructionsModel: InstructionsModel
): ViewModel() {

    private val instructions = arrayListOf<InstructionData>()
    private var count = 0

    private val _stateInstructionData: MutableStateFlow<InstructionData> = MutableStateFlow(initInstructionData())
    val stateInstructionData: StateFlow<InstructionData> = _stateInstructionData

    init {
        instructions.addAll(instructionsModel.loadInstruction())
    }

    fun restart() {
        count = 0
        viewModelScope.launch{
            _stateInstructionData.emit(initInstructionData())
        }
    }

    fun getNextInstruction() {
        viewModelScope.launch{
            if (count < instructions.size)
                _stateInstructionData.emit(instructions[count++])
        }
    }

    private fun initInstructionData() = InstructionData(R.drawable.icon_fly, "Инструкция")
}