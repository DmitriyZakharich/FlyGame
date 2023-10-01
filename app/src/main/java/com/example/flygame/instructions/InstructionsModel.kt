package com.example.flygame.instructions

import android.util.Log
import com.example.flygame.R
import javax.inject.Inject

class InstructionsModel @Inject constructor(){

    fun loadInstruction(): List<InstructionData> {

        val instructions = mutableListOf<InstructionData>()
        instructions.add(InstructionData(R.drawable.icon_fly, "Муха - это игра для развития внимания и пространственного ориентирования"))
        instructions.add(InstructionData(R.drawable.ic_launcher_background, "Поле игры представляет из себя таблицу с мухой, расположенной в центре"))
        instructions.add(InstructionData(R.drawable.ic_launcher_foreground, "При нажатии кнопки \"Старт\" даются команды и нужно отслеживать движение мухи по таблице"))
        instructions.add(InstructionData(R.drawable.ic_launcher_foreground, "После окончания команд нажмите на ячейку для проверки"))
        instructions.add(InstructionData(R.drawable.ic_launcher_foreground, "Для лучшего эффекта выключите отображение поля и представляйте его у себя в воображении. Глаза закрывать не нужно.", Event.ON_COMPLETED))
        return instructions
    }

}