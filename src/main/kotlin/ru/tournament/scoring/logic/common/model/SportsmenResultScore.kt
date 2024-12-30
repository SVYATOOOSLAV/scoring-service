package ru.tournament.scoring.logic.common.model

import ru.tournament.scoring.logic.common.enums.Step

data class SportsmenResultScore(
    val resultScore: Double,
    val typeStep: Step
)