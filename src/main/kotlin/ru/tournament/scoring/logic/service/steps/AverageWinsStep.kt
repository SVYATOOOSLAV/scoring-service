package ru.tournament.scoring.logic.service.steps

import org.springframework.stereotype.Component
import ru.tournament.model.SportsmenGamesRequest
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.ScoringStep
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService

@Component
class AverageWinsStep(
    private val boxMakerService: BoxMakerService,
    private val tournamentStorageService: TournamentStorageService
) : ScoringStep {

    override fun calculate(info: SportsmenInfo): Double {
        val gamesRequest = SportsmenGamesRequest(info.id, info.sport)
        val gamesList = tournamentStorageService.getSportsmenGames(gamesRequest)
        return if (gamesList.isNotEmpty()) boxMakerService.calculateAverageForGames(gamesList) else 0.0
    }
}