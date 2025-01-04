package ru.tournament.scoring.logic.service.impl.box.steps

import org.springframework.stereotype.Component
import ru.tournament.scoring.BASE_PERIOD
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.ScoreStepResult
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.scoring.logic.service.impl.box.BoxScoringStep
import ru.tournament.storage.dto.SportsmenGamesRequest

@Component
class WinnerCoefficientBoxStep(
    private val boxMakerService: BoxMakerService,
    private val tournamentStorageService: TournamentStorageService
) : BoxScoringStep {
    override fun step(): Step = Step.WINNER

    override fun calculate(info: SportsmenInfo): ScoreStepResult {
        val gamesPeriodRequest = SportsmenGamesRequest(
            id = info.id,
            sport = info.sport,
            period = info.periodValidation ?: BASE_PERIOD
        )
        val winsLastYear = tournamentStorageService.getSportsmenGames(gamesPeriodRequest)
        val result = boxMakerService.calculateLastWins(winsLastYear)
        return ScoreStepResult(
            resultScore = result,
            typeStep = step()
        )
    }
}
