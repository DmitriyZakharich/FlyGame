package com.example.flygame.instructions

import com.example.flygame.R
import javax.inject.Inject

class InstructionsModel @Inject constructor(){

    val generalInstructions = mutableListOf<InstructionData>().apply {
        add(InstructionData(R.drawable.icon_fly, "Муха - это игра для развития внимания и пространственного ориентирования"))
        add(InstructionData(R.drawable.ic_launcher_background, "Поле игры представляет из себя таблицу с мухой, расположенной в центре"))
        add(InstructionData(R.drawable.ic_launcher_foreground, "При нажатии кнопки \"Старт\" даются команды и нужно отслеживать движение мухи по таблице"))
        add(InstructionData(R.drawable.ic_launcher_foreground, "После окончания команд нажмите на ячейку для проверки"))
        add(InstructionData(R.drawable.ic_launcher_foreground, "Для лучшего эффекта выключите отображение поля и представляйте его у себя в воображении. Глаза закрывать не нужно.", Event.ON_COMPLETED))
    }

    val instructionsVolumetricField = mutableListOf<InstructionData>().apply {
        add(InstructionData(R.drawable.icon_fly, "Объемное поле"))
        add(InstructionData(R.drawable.icon_fly, "Представьте, что есть глубина"))
        add(InstructionData(R.drawable.ic_launcher_background, "Муха может двигаться вперед и назад", Event.ON_COMPLETED))
    }
}