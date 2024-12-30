package ru.tournament.scoring.logic.service.impl.box

import org.springframework.stereotype.Service
import ru.tournament.model.SportsmenInfoRequest
import ru.tournament.model.SportsmenRateRequest
import ru.tournament.model.SportsmenRequestDto
import ru.tournament.scoring.BASE_SCORE
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.Result
import ru.tournament.scoring.logic.exception.BusinessLogicException
import ru.tournament.scoring.logic.exception.Codes
import ru.tournament.scoring.logic.mapper.toSportsmenInfo
import ru.tournament.scoring.logic.service.ScoringService

@Service
class BoxService(
    private val steps: List<BoxScoringStep>,
    private val tournamentStorageService: TournamentStorageService
) : ScoringService {

    override fun type(): Sport = Sport.BOX

    override fun score(sportsmenRequestDto: SportsmenRequestDto): Result {
        val sportsmenInfo = tournamentStorageService.getSportsmenInfo(
            SportsmenInfoRequest(sportsmenRequestDto.sportsmenId, sportsmenRequestDto.sport)
        ).toSportsmenInfo(sportsmenRequestDto.period)

        val coefficients = steps.map { it.calculate(sportsmenInfo) }

        val finalScore = coefficients.fold(BASE_SCORE) { acc, coefficient ->
            when (coefficient.typeStep) {
                Step.SANCTIONS -> acc - coefficient.resultScore
                else -> acc + coefficient.resultScore
            }
        }

        val sportsmenRateRequest = SportsmenRateRequest(
            sportsmenRequestDto.sportsmenId,
            type().value,
            finalScore
        )
        val result = tournamentStorageService.updateRateSportsmen(sportsmenRateRequest)

        if (!result.isSuccess()) {
            throw BusinessLogicException(Codes.PROCEDURE_ERROR)
        }

        return result
    }
}