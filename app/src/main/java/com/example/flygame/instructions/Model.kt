package com.example.flygame.instructions

data class InstructionData(
    val image: Int,
    val instruction: String
)

enum class TypeInstruction {
    GENERAL,
    VOLUMETRIC_FIELD
}