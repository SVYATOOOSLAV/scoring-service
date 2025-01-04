package ru.tournament.scoring.logic.service.impl.box.steps

import org.springframework.stereotype.Component
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.ScoreStepResult
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.scoring.logic.service.impl.box.BoxScoringStep
import ru.tournament.storage.dto.SportsmenGamesRequest

@Component
class AverageWinsBoxStep(
    private val boxMakerService: BoxMakerService,
    private val tournamentStorageService: TournamentStorageService
) : BoxScoringStep {

    override fun step(): Step = Step.AVERAGE_WINS

    override fun calculate(info: SportsmenInfo): ScoreStepResult {
        val gamesRequest = SportsmenGamesRequest(info.id, info.sport)
        val gamesList = tournamentStorageService.getSportsmenGames(gamesRequest)
        val result = boxMakerService.calculateAverageForGames(gamesList)
        return ScoreStepResult(
            resultScore = result,
            typeStep = step()
        )
    }
}
