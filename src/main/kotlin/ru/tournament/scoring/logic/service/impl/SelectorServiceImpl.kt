package ru.tournament.scoring.logic.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tournament.model.SportsmenRequestDto
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Result
import ru.tournament.scoring.logic.service.ScoringService
import ru.tournament.scoring.logic.service.SelectorService

@Service
class SelectorServiceImpl(
    @Autowired private val sportMap: Map<Sport, ScoringService>
) : SelectorService {

    override fun score(sportsmen: SportsmenRequestDto): Result {
        val sport = Sport.valueOf(sportsmen.sport.uppercase())

        val service = sportMap[sport]
            ?: throw IllegalArgumentException("Sport ${sportsmen.sport} not found")

        return service.score(sportsmen)
    }
}
