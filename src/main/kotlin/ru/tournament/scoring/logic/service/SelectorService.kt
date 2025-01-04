package ru.tournament.scoring.logic.service

import ru.tournament.scoring.dto.SportsmenRequestDto
import ru.tournament.scoring.logic.common.model.Result

interface SelectorService {
    fun score(sportsmen: SportsmenRequestDto): Result
}
