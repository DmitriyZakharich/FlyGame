package com.example.flygame.instructions.repository

import com.example.flygame.App.Companion.appContext
import com.example.flygame.R
import com.example.flygame.instructions.models.InstructionData
import javax.inject.Inject

class InstructionsModel @Inject constructor() {

    val generalInstructions = mutableListOf(
        InstructionData( R.drawable.icon_fly, appContext.getString(R.string.general_instructions_1) ),
        InstructionData( R.drawable.ic_launcher_background, appContext.getString(R.string.general_instructions_2) ),
        InstructionData( R.drawable.ic_launcher_foreground, appContext.getString(R.string.general_instructions_3) ),
        InstructionData( R.drawable.ic_launcher_foreground, appContext.getString(R.string.general_instructions_4) ),
        InstructionData( R.drawable.ic_launcher_foreground, appContext.getString(R.string.general_instructions_5) )
    )

    val instructionsVolumetricField = mutableListOf(
        InstructionData(R.drawable.icon_fly, appContext.getString(R.string.instructions_volumetric_field_1)),
        InstructionData(R.drawable.icon_fly, appContext.getString(R.string.instructions_volumetric_field_2)),
        InstructionData( R.drawable.ic_launcher_background, appContext.getString(R.string.instructions_volumetric_field_3) )
    )

    val instructionsHideField = mutableListOf(
        InstructionData( R.drawable.icon_fly, appContext.getString(R.string.instructions_hide_field_1) )
    )
}