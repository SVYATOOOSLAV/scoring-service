package ru.tournament.scoring.logic.service

import ru.tournament.scoring.logic.common.dto.SportsmenResponse
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Sportsmen

interface ScoringService {
    fun type(): Sport
    fun score(sportsmen: Sportsmen): SportsmenResponse
}