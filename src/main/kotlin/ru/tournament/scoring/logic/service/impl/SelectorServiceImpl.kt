package ru.tournament.scoring.logic.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tournament.scoring.logic.common.dto.SportsmenResponse
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Sportsmen
import ru.tournament.scoring.logic.service.ScoringService
import ru.tournament.scoring.logic.service.SelectorService

@Service
class SelectorServiceImpl(
    @Autowired private val sportMap: Map<Sport, ScoringService>
): SelectorService {

    override fun selectScoringBySport(sportsmen: Sportsmen): SportsmenResponse {
       val sportService = sportMap[sportsmen.sport]
           ?: throw IllegalArgumentException("Sport ${sportsmen.sport} not found")

        sportService.score(sportsmen)

        return SportsmenResponse()
    }
}