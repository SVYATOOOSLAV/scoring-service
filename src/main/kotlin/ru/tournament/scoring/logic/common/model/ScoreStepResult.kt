package ru.tournament.scoring.logic.common.model

import ru.tournament.scoring.logic.common.enums.Step

data class ScoreStepResult(
    val resultScore: Double,
    val typeStep: Step
)
