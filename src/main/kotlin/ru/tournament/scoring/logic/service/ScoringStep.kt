package ru.tournament.scoring.logic.service

import ru.tournament.scoring.logic.common.model.SportsmenInfo

interface ScoringStep {
    fun calculate(info: SportsmenInfo): Double
}