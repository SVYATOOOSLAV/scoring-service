package ru.tournament.scoring.logic.service.impl

import org.springframework.stereotype.Service
import ru.tournament.model.SportsmenRequestDto
import ru.tournament.scoring.BASE_SCORE
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Result
import ru.tournament.scoring.logic.exception.BusinessLogicException
import ru.tournament.scoring.logic.service.ScoringService
import java.time.LocalDate
import java.time.Period

@Service
class BoxService(
    private val boxMakerService: BoxMakerService,
    private val tournamentStorageService: TournamentStorageService
) : ScoringService {

    override fun type(): Sport = Sport.BOX

    override fun score(sportsmenRequestDto: SportsmenRequestDto): Result {
        val sportsmenInfo = tournamentStorageService.getSportsmenInfo(
            sportsmenRequestDto.sportsmenId,
            sportsmenRequestDto.sport
        )

        // step 1 done
        // step 2 in thinking client
        var averageOfWin = 0
        val listOfGames = tournamentStorageService.getAllSportsmenGames(sportsmenInfo.id, type().value)

        if (listOfGames.isNotEmpty()) {
            averageOfWin = boxMakerService.calculateAverageForGames(listOfGames)
        }

        // step 3
        val coefAge = boxMakerService.calculateAge(
            age = Period.between(sportsmenInfo.birthday, LocalDate.now()).years
        )

        val coefWeight = boxMakerService.calculateWeight(
            weight = sportsmenInfo.weight,
            isMale = sportsmenInfo.isMale
        )

        var coefHeight = boxMakerService.calculateHeight(
            height = sportsmenInfo.height
        )

        // step 4 in thinking client
        var coefSanction = 1.0
        val listOfSanction = tournamentStorageService.getSportsmenSanctionLastPeriod(
            sportsmenInfo.id,
            type().value,
            sportsmenRequestDto.period
        )
        if (listOfSanction.isNotEmpty()) {
            coefSanction = boxMakerService.calculateSanctions(listOfSanction)
        }

        // step 5 in thinking client
        var coefWinner = 0.5
        val listWinsLastYear = tournamentStorageService.getSportsmenGamesLastPeriod(
            sportsmenInfo.id,
            type().value,
            sportsmenRequestDto.period
        )
        if (listWinsLastYear.isNotEmpty()) {
            coefWinner = boxMakerService.calculateSanctions(listOfSanction)
        }

        // step 6 result
        var rate = (BASE_SCORE + averageOfWin) * coefAge * coefWeight * coefHeight * coefSanction * coefWinner

        var result = tournamentStorageService.updateRateSportsmen(sportsmenInfo.id, type().value, rate)

        if (!result.isSuccess()) {
            throw BusinessLogicException(-99, "Ошибка выполнения процедуры")
        }

        return result
    }
}
