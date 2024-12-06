package ru.tournament.scoring.logic.service.impl.box

import org.springframework.stereotype.Service
import ru.tournament.model.SportsmenInfoRequest
import ru.tournament.model.SportsmenRateRequest
import ru.tournament.model.SportsmenRequestDto
import ru.tournament.scoring.BASE_SCORE
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Result
import ru.tournament.scoring.logic.exception.BusinessLogicException
import ru.tournament.scoring.logic.mapper.toSportsmenInfo
import ru.tournament.scoring.logic.service.ScoringService
import ru.tournament.scoring.logic.service.ScoringStep

@Service
class BoxService(
    private val steps: List<ScoringStep>,
    private val tournamentStorageService: TournamentStorageService
) : ScoringService {

    override fun type(): Sport = Sport.BOX

    override fun score(sportsmenRequestDto: SportsmenRequestDto): Result {
        // Шаг 1: Подготовка контекста
        val sportsmenInfo = tournamentStorageService.getSportsmenInfo(
            SportsmenInfoRequest(sportsmenRequestDto.sportsmenId, sportsmenRequestDto.sport)
        ).toSportsmenInfo(sportsmenRequestDto.period)

        // Шаг 2: Расчет через шаги
        val coefficients = steps.map { it.calculate(sportsmenInfo) }
        val finalScore = BASE_SCORE + coefficients
            .filter { it > 0 }
            .reduce { acc, coef -> acc * coef }

        // Шаг 3: Обновление рейтинга
        val sportsmenRateRequest = SportsmenRateRequest(
            sportsmenRequestDto.sportsmenId,
            type().value,
            finalScore
        )
        val result = tournamentStorageService.updateRateSportsmen(sportsmenRateRequest)

        if (!result.isSuccess()) {
            throw BusinessLogicException(-99, "Ошибка выполнения процедуры")
        }

        return Result(0, "")
    }
}