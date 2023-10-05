package com.example.flygame.instructions

data class InstructionData(
    val image: Int,
    val instruction: String,
    val event: Event = Event.ON_NEXT
)

enum class Event {
    ON_NEXT,
    ON_COMPLETED
}

enum class TypeInstruction {
    GENERAL,
    VOLUMETRIC_FIELD
}