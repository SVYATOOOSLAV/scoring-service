package ru.tournament.scoring.logic.service.impl

import org.springframework.stereotype.Service
import ru.tournament.scoring.dto.SportsmenRequestDto
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Result
import ru.tournament.scoring.logic.service.ScoringService
import ru.tournament.scoring.logic.service.SelectorService

@Service
class SelectorServiceImpl(
    private val sportMap: Map<Sport, ScoringService>
) : SelectorService {

    override fun score(sportsmen: SportsmenRequestDto): Result {
        val sport = Sport.toEnum(sportsmen.sport)

        val service = requireNotNull(sportMap[sport]) {
            "Scoring service not found for enum ${sportsmen.sport}"
        }

        return service.score(sportsmen)
    }
}
