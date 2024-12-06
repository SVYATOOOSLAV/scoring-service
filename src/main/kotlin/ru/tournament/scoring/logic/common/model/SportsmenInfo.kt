package ru.tournament.scoring.logic.common.model

import java.time.LocalDate

data class SportsmenInfo(
    val id: Int,
    val sport: String,
    val isMale: Boolean,
    val birthday: LocalDate,
    val weight: Double?,
    val height: Double?,
    val periodValidation: Int?
)
