package ru.tournament.scoring.logic.common.model

import ru.tournament.scoring.logic.common.enums.Sport
import java.time.LocalDate

@SuppressWarnings("EmptyClassBlock")
data class Sportsmen(
    val id: Long,
//    val name: String,
//    val surname: String,
    val birthday: LocalDate,
    val sport: Sport,
//    val rate: Double,
    val weight: Double,
    val isMale: Boolean,
    val height: Double,
)
