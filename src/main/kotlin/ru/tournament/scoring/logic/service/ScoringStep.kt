package ru.tournament.scoring.logic.service

import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.common.model.SportsmenResultScore

interface ScoringStep {
    fun step(): Step
    fun calculate(info: SportsmenInfo): SportsmenResultScore
}