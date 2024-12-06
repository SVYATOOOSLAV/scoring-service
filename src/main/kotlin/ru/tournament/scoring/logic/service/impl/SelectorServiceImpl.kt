package ru.tournament.scoring.logic.service.impl

import org.springframework.stereotype.Service
import ru.tournament.model.SportsmenRequestDto
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Result
import ru.tournament.scoring.logic.exception.Codes
import ru.tournament.scoring.logic.exception.ValidationErrorException
import ru.tournament.scoring.logic.service.ScoringService
import ru.tournament.scoring.logic.service.SelectorService

@Service
class SelectorServiceImpl(
    private val sportMap: Map<Sport, ScoringService>
) : SelectorService {

    override fun score(sportsmen: SportsmenRequestDto): Result {
        val sport = Sport.toEnum(sportsmen.sport)

        val service = sportMap[sport]

        return service!!.score(sportsmen)
    }
}
