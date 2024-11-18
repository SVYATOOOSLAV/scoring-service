package ru.tournament.scoring.logic.service

import ru.tournament.model.SportsmenRequestDto
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Result

interface ScoringService {
    fun type(): Sport
    fun score(sportsmenRequestDto: SportsmenRequestDto): Result
}
