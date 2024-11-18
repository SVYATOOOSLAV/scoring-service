package ru.tournament.scoring.logic.common.dto

import java.time.LocalDate

@SuppressWarnings("EmptyClassBlock")
data class SportsmenResponse(
    val name: String? = null,
    val surname: String? = null,
    val birthday: LocalDate? = null,
    val sport: String? = null,
    val rate: Double? = null,
)
