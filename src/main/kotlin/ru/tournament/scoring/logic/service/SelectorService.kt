package ru.tournament.scoring.logic.service

import ru.tournament.scoring.logic.common.dto.SportsmenResponse
import ru.tournament.scoring.logic.common.model.Sportsmen

interface SelectorService {
    fun selectScoringBySport(sportsmen: Sportsmen): SportsmenResponse
}