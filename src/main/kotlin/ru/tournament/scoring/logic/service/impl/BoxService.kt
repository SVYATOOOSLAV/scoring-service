package ru.tournament.scoring.logic.service.impl

import org.springframework.stereotype.Service
import ru.tournament.scoring.logic.common.dto.SportsmenResponse
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Sportsmen
import ru.tournament.scoring.logic.service.ScoringService

@Service
class BoxService: ScoringService {
    override fun type(): Sport = Sport.BOX

    override fun score(sportsmen: Sportsmen): SportsmenResponse {
        TODO("Not yet implemented")
    }
}