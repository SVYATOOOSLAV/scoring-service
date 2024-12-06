package ru.tournament.scoring.logic.service.steps

import org.springframework.stereotype.Component
import ru.tournament.model.SportsmenGamesRequest
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.ScoringStep
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService

@Component
class WinnerCoefficientStep(
    private val boxMakerService: BoxMakerService,
    private val tournamentStorageService: TournamentStorageService
) : ScoringStep {

    override fun calculate(info: SportsmenInfo): Double {
        val gamesPeriodRequest = SportsmenGamesRequest(
            info.id,
            info.sport,
            info.periodValidation
        )
        val winsLastYear = tournamentStorageService.getSportsmenGames(gamesPeriodRequest)
        return if (winsLastYear.isNotEmpty()) boxMakerService.calculateLastWins(winsLastYear) else 0.5
    }
}