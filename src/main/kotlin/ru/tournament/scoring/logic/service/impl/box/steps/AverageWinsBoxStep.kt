package ru.tournament.scoring.logic.service.impl.box.steps

import org.springframework.stereotype.Component
import ru.tournament.model.SportsmenGamesRequest
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.common.model.SportsmenResultScore
import ru.tournament.scoring.logic.service.ScoringStep
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.scoring.logic.service.impl.box.BoxScoringStep

@Component
class AverageWinsBoxStep(
    private val boxMakerService: BoxMakerService,
    private val tournamentStorageService: TournamentStorageService
) : BoxScoringStep {

    override fun step(): Step = Step.AVERAGE_WINS

    override fun calculate(info: SportsmenInfo): SportsmenResultScore {
        val gamesRequest = SportsmenGamesRequest(info.id, info.sport)
        val gamesList = tournamentStorageService.getSportsmenGames(gamesRequest)
        val result = boxMakerService.calculateAverageForGames(gamesList)
        return SportsmenResultScore(
            resultScore = result,
            typeStep = step()
        )
    }
}