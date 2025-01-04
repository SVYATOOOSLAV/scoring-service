package ru.tournament.scoring.logic.service

import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.ScoreStepResult
import ru.tournament.scoring.logic.common.model.SportsmenInfo

interface ScoringStep {
    fun step(): Step
    fun calculate(info: SportsmenInfo): ScoreStepResult
}
